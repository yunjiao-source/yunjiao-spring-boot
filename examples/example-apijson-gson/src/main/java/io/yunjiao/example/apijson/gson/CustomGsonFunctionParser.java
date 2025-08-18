package io.yunjiao.example.apijson.gson;

import apijson.JSONRequest;
import apijson.NotNull;
import apijson.StringUtil;
import apijson.orm.AbstractVerifier;
import apijson.orm.Visitor;
import io.yunjiao.springboot.autoconfigure.apijson.ApijsonUtils;
import io.yunjiao.springboot.autoconfigure.apijson.gson.GsonFunctionParser;
import io.yunjiao.springboot.autoconfigure.apijson.gson.GsonVerifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * 远程函数
 *
 * @author yangyunjiao
 */
public class CustomGsonFunctionParser extends GsonFunctionParser {
    /**
     * 标识
     */
    public static final  String TAG = CustomGsonFunctionParser.class.getSimpleName();

    // APIJSONFunctionParser.invoke方法中，使用<code>Method m = cls.getMethod(methodName, parameterTypes)</code>获取
    // 方法时异常NoSuchMethodException，原因是：通过反射（如 getMethod()）调用方法，反射API会严格匹配参数类型
    // （LinkedHashMap.class 与 Map.class 被视为不同类型）
    //
    // 这里只是演示一种解决方式

    //@Override
    public boolean isContain(LinkedHashMap<String, Object> curObj, String array, String value) {
        return super.isContain(curObj, array, value);
    }

    //@Override
    public boolean isContainKey(@NotNull LinkedHashMap<String, Object> curObj, String object, String key) {
        return super.isContainKey(curObj, object, key);
    }

    //@Override
    public boolean isContainValue(@NotNull LinkedHashMap<String, Object> curObj, String object, String value) {
        return super.isContainValue(curObj, object, value);
    }

    //@Override
    public Object getFromArray(@NotNull LinkedHashMap<String, Object> curObj, String array, String position) {
        return super.getFromArray(curObj, array, position);
    }

    //@Override
    public Object getFromObject(@NotNull LinkedHashMap<String, Object> curObj, String object, String key) {
        return super.getFromObject(curObj, object, key);
    }

    //@Override
    public int countObject(@NotNull LinkedHashMap<String, Object> curObj, String object) {
        return super.countObject(curObj, object);
    }

    //@Override
    public int countArray(@NotNull LinkedHashMap<String, Object> curObj, String array) {
        return super.countArray(curObj, array);
    }


    public Visitor<Serializable> getCurrentUser(@NotNull LinkedHashMap<String, Object> curObj) {
        return GsonVerifier.getVisitor(getSession());
    }

    public Serializable getCurrentUserId(@NotNull LinkedHashMap<String, Object> curObj) {
        return GsonVerifier.getVisitorId(getSession());
    }

    public List<Serializable> getCurrentUserIdAsList(@NotNull LinkedHashMap<String, Object> curObj) {
        List<Serializable> list = new ArrayList<>(1);
        list.add(GsonVerifier.getVisitorId(getSession()));
        return list;
    }

    public List<Serializable> getCurrentContactIdList(@NotNull LinkedHashMap<String, Object> curObj) {
        Visitor<Serializable> user = getCurrentUser(curObj);
        return user == null ? null : user.getContactIdList();
    }


    public void verifyIdList(@NotNull LinkedHashMap<String, Object> curObj, @NotNull String idList) throws Exception {
        Object obj = getArgVal(idList);
        if (obj == null) {
            return;
        }

        ApijsonUtils.checkItemInList(obj, (item, index) -> {
            boolean isTrue = item instanceof Number || item instanceof String;
            if (!isTrue) {
                throw new IllegalArgumentException(idList + "/" + index + ": " + item + " 不符合 Number 或 String 类型!");
            }
        });
    }

    public void verifyURLList(@NotNull LinkedHashMap<String, Object> curObj, @NotNull String urlList) throws Exception {
        Object obj = getArgVal(urlList);
        if (obj == null) {
            return;
        }

        ApijsonUtils.checkItemInList(obj, (item, index) -> {
            boolean isTrue = item instanceof String && StringUtil.isUrl((String) item);
            if (!isTrue) {
                throw new IllegalArgumentException(urlList + "/" + index + ": " + item + " 不符合 URL 字符串格式!");
            }
        });
    }

    /**
     * TODO 仅用来测试 "key-()":"verifyAccess()"
     */
    public Object verifyAccess(@NotNull LinkedHashMap<String, Object> curObj) throws Exception {
        String role = getArgStr(JSONRequest.KEY_ROLE);
        Long userId = getArgLong(JSONRequest.KEY_USER_ID);
        if (AbstractVerifier.OWNER.equals(role) && !Objects.equals(userId, GsonVerifier.getVisitorId(getSession()))) {
            throw new IllegalAccessException("登录用户与角色OWNER不匹配！");
        }
        return null;
    }

    public void verifyGroupUrlLike(@NotNull LinkedHashMap<String, Object> curObj, String urlLike) throws Exception {
        String urlLikeVal = getArgStr(urlLike);

        if (StringUtil.isEmpty(urlLikeVal) || !urlLikeVal.endsWith("/%")) {
            throw new IllegalArgumentException(urlLike + "必须以 /% 结尾！");
        }

        String url = urlLikeVal.substring(0, urlLike.length() - 2);
        String rest = url.replaceAll("_", "")
                .replaceAll("%", "%")
                .replaceAll("/", "%")
                .trim();

        if (StringUtil.isEmpty(rest)) {
            throw new IllegalArgumentException(urlLike + "必须以包含有效 URL 字符！");
        }
    }


}
