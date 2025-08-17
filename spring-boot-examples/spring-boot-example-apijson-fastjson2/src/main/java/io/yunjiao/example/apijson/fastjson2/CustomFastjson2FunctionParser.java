package io.yunjiao.example.apijson.fastjson2;

import apijson.NotNull;
import apijson.RequestMethod;
import apijson.StringUtil;
import apijson.fastjson2.JSON;
import apijson.fastjson2.JSONRequest;
import apijson.fastjson2.JSONResponse;
import apijson.orm.AbstractVerifier;
import apijson.orm.Visitor;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.yunjiao.spring.boot.autoconfigure.apijson.ApijsonUtils;
import io.yunjiao.spring.boot.autoconfigure.apijson.fastjson2.Fastjson2FunctionParser;
import io.yunjiao.spring.boot.autoconfigure.apijson.fastjson2.Fastjson2Parser;
import io.yunjiao.spring.boot.autoconfigure.apijson.fastjson2.Fastjson2Verifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 远程函数
 *
 * @author yangyunjiao
 */
public class CustomFastjson2FunctionParser extends Fastjson2FunctionParser {
    /**
     * 标识
     */
    public static final  String TAG = CustomFastjson2FunctionParser.class.getSimpleName();

    public Visitor<Serializable> getCurrentUser(@NotNull JSONObject curObj) {
        return Fastjson2Verifier.getVisitor(getSession());
    }

    public Serializable getCurrentUserId(@NotNull JSONObject curObj) {
        return Fastjson2Verifier.getVisitorId(getSession());
    }

    public List<Serializable> getCurrentUserIdAsList(@NotNull JSONObject curObj) {
        List<Serializable> list = new ArrayList<>(1);
        list.add(Fastjson2Verifier.getVisitorId(getSession()));
        return list;
    }

    public List<Serializable> getCurrentContactIdList(@NotNull JSONObject curObj) {
        Visitor<Serializable> user = getCurrentUser(curObj);
        return user == null ? null : user.getContactIdList();
    }


    public void verifyIdList(@NotNull JSONObject curObj, @NotNull String idList) throws Exception {
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

    public void verifyURLList(@NotNull JSONObject curObj, @NotNull String urlList) throws Exception {
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


    public int deleteCommentOfMoment(@NotNull JSONObject curObj, @NotNull String momentId) throws Exception {
        Long mid = getArgLong(momentId);
        if (mid == null || mid <= 0 || curObj.getIntValue(JSONResponse.KEY_COUNT) <= 0) {
            return 0;
        }

        JSONObject request = JSON.newJSONObject();

        //Comment<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        JSONObject comment = JSON.newJSONObject();
        comment.put("momentId", mid);

        request.put("Comment", comment);
        //Comment>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        JSONObject rp = new Fastjson2Parser(RequestMethod.DELETE).setNeedVerify(false).parseResponse(request);

        JSONObject c = rp.getJSONObject("Comment");
        return c == null ? 0 : c.getIntValue(JSONResponse.KEY_COUNT);
    }


    /**
     * 删除评论的子评论
     */
    public int deleteChildComment(@NotNull JSONObject curObj, @NotNull String toId) throws Exception {
        Long tid = getArgLong(toId);
        if (tid == null || tid <= 0 || curObj.getIntValue(JSONResponse.KEY_COUNT) <= 0) {
            return 0;
        }

        //递归获取到全部子评论id

        JSONObject request = JSON.newJSONObject();

        //Comment<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        JSONObject comment = JSON.newJSONObject();
        comment.put("id{}", getChildCommentIdList(tid));

        request.put("Comment", comment);
        //Comment>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        JSONObject rp = new Fastjson2Parser(RequestMethod.DELETE).setNeedVerify(false).parseResponse(request);

        JSONObject c = rp.getJSONObject("Comment");
        return c == null ? 0 : c.getIntValue(JSONResponse.KEY_COUNT);
    }


    private JSONArray getChildCommentIdList(long tid) {
        JSONArray arr = new JSONArray();

        JSONObject request = JSON.newJSONObject();

        //Comment-id[]<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        JSONRequest idItem = new JSONRequest();

        //Comment<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        JSONRequest comment = new JSONRequest();
        comment.put("toId", tid);
        comment.setColumn("id");
        idItem.put("Comment", JSON.newJSONObject(comment));
        //Comment>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        request.putAll(JSON.newJSONObject(idItem.toArray(0, 0, "Comment-id")));
        //Comment-id[]>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        JSONObject rp = new Fastjson2Parser().setNeedVerify(false).parseResponse(request);

        JSONArray a = rp.getJSONArray("Comment-id[]");
        if (a != null) {
            arr.addAll(a);

            for (int i = 0; i < a.size(); i++) {

                JSONArray a2 = getChildCommentIdList(a.getLongValue(i));
                if (a2 != null) {
                    arr.addAll(a2);
                }
            }
        }

        return arr;
    }



    /**
     * TODO 仅用来测试 "key-()":"verifyAccess()"
     */
    public Object verifyAccess(@NotNull JSONObject curObj) throws Exception {
        String role = getArgStr(JSONRequest.KEY_ROLE);
        Long userId = getArgLong(JSONRequest.KEY_USER_ID);
        if (AbstractVerifier.OWNER.equals(role) && !Objects.equals(userId, Fastjson2Verifier.getVisitorId(getSession()))) {
            throw new IllegalAccessException("登录用户与角色OWNER不匹配！");
        }
        return null;
    }

    public void verifyGroupUrlLike(@NotNull JSONObject curObj, String urlLike) throws Exception {
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
