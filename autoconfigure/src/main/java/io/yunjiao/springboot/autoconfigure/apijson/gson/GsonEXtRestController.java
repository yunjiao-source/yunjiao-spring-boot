package io.yunjiao.springboot.autoconfigure.apijson.gson;

import apijson.Log;
import apijson.StringUtil;
import apijson.framework.BaseModel;
import apijson.gson.APIJSONController;
import apijson.gson.JSON;
import apijson.gson.JSONRequest;
import apijson.gson.JSONResponse;
import apijson.orm.exception.ConditionErrorException;
import apijson.orm.exception.ConflictException;
import apijson.orm.exception.NotExistException;
import io.yunjiao.extension.apjson.annotation.ApijsonRest;
import io.yunjiao.extension.apjson.orm.GsonMap;
import io.yunjiao.springboot.autoconfigure.apijson.gson.model.Privacy;
import io.yunjiao.springboot.autoconfigure.apijson.gson.model.User;
import io.yunjiao.springboot.autoconfigure.apijson.gson.model.Verify;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.rmi.ServerException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import static apijson.JSON.getString;
import static apijson.RequestMethod.*;
import static apijson.framework.APIJSONConstant.*;

/**
 * 统一的rest接口
 *
 * @author yangyunjiao
 */
@Slf4j
@ApijsonRest
@RequiredArgsConstructor
@RestController
@RequestMapping("ext")
public class GsonEXtRestController extends APIJSONController<Serializable> {
    public static final  String USER_;

    public static final  String PRIVACY_;

    public static final  String VERIFY_; //加下划线后缀是为了避免 Verify 和 verify 都叫VERIFY，分不清

    public static final  String CURRENT_USER_ID = "currentUserId";

    public static final  String NAME = "name";

    public static final  String PHONE = "phone";

    public static final  String PASSWORD = "password";

    public static final  String _PASSWORD = "_password";

    public static final  String _PAY_PASSWORD = "_payPassword";

    public static final  String OLD_PASSWORD = "oldPassword";

    public static final  String VERIFY = "verify";

    public static final  String TYPE = "type";

    public static final  String VALUE = "value";

    public static final  String LOGIN = "login";

    public static final  String AS_DB_ACCOUNT = "asDBAccount";

    public static final  String REMEMBER = "remember";

    public static final  String DEFAULTS = "defaults";

    public static final  int LOGIN_TYPE_PASSWORD = 0;//密码登录

    public static final  int LOGIN_TYPE_VERIFY = 1;//验证码登录

    static {
        USER_ = User.class.getSimpleName();
        PRIVACY_ = Privacy.class.getSimpleName();
        VERIFY_ = Verify.class.getSimpleName();
    }

    private static final String REGISTER = "register";

    /**重新加载配置
     * @param request 请求
     * @return
     * <pre>
    {
    "type": "ALL",  //重载对象，ALL, FUNCTION, REQUEST, ACCESS，非必须
    "phone": "13000082001",
    "verify": "1234567", //验证码，对应类型为 Verify.TYPE_RELOAD
    "value": {  // 自定义增量更新条件
    "id": 1  // 过滤条件，符合 APIJSON 查询功能符即可
    }
    }
     * </pre>
     */
    @PostMapping("reload")
    @Override
    public Map<String, Object> reload(@RequestBody String request) {
        GsonMap<String, Object> requestObject = null;
        String type;
        Map<String, Object> value;
        String phone;
        String verify;
        try {
            requestObject = GsonMap.of(GsonParser.parseRequest(request));
            type = requestObject.getString(TYPE);
            value = requestObject.getJSONObject(VALUE);
            phone = requestObject.getString(PHONE);
            verify = requestObject.getString(VERIFY);
        } catch (Exception e) {
            return extendErrorResult(requestObject, e);
        }

        JSONResponse response = new JSONResponse(headVerify(Verify.TYPE_RELOAD, phone, verify));
        response = response.getJSONResponse(VERIFY_);
        if (!JSONResponse.isExist(response)) {
            return extendErrorResult(requestObject, new ConditionErrorException("手机号或验证码错误"));
        }

        Map<String, Object> result = newSuccessResult();

        boolean reloadAll = StringUtil.isEmpty(type, true) || "ALL".equals(type);

        if (reloadAll || "ACCESS".equals(type)) {
            try {
                result.put(ACCESS_, GsonVerifier.initAccess(false, null, value));
            } catch (ServerException e) {
                e.printStackTrace();
                result.put(ACCESS_, newErrorResult(e));
            }
        }

        if (reloadAll || "FUNCTION".equals(type)) {
            try {
                result.put(FUNCTION_, GsonFunctionParser.init(false, null, value));
            } catch (ServerException e) {
                e.printStackTrace();
                result.put(FUNCTION_, newErrorResult(e));
            }
        }

        if (reloadAll || "REQUEST".equals(type)) {
            try {
                result.put(REQUEST_, GsonVerifier.initRequest(false, null, value));
            } catch (ServerException e) {
                e.printStackTrace();
                result.put(REQUEST_, newErrorResult(e));
            }
        }

        return result;
    }

    /**生成验证码,修改为post请求
     * @param request 请求
     * <pre>
            {
            "type": 0,  //类型，0,1,2,3,4，非必须
            "phone": "13000082001"
            }
     * </pre>
     */
    @PostMapping("post/verify")
    public Map<String, Object> postVerify(@RequestBody String request) {
        GsonMap<String, Object> requestObject = null;
        int type;
        String phone;
        try {
            requestObject = GsonMap.of(GsonParser.parseRequest(request));
            type = requestObject.getIntValue(TYPE);
            phone = requestObject.getString(PHONE);
        } catch (Exception e) {
            return extendErrorResult(requestObject, e);
        }

        new GsonParser(DELETE, false).parse(newVerifyRequest(type, phone));

        Map<String, Object> response = new GsonParser(POST, false).parseResponse(
                newVerifyRequest(type, phone, "" + (new Random().nextInt(9999) + 1000))
        );


        if (!JSONResponse.isSuccess(response)) {
            new GsonParser(DELETE, false).parseResponse(newVerifyRequest(type, phone));
            return response;
        }

        //TODO 这里直接返回验证码，方便测试。实际上应该只返回成功信息，验证码通过短信发送
        Map<String, Object> object = new GsonMap<>();
        object.put(TYPE, type);
        object.put(PHONE, phone);
        return getVerify(JSON.toJSONString(object));
    }

    /**获取验证码
     * @param request
     * <pre>
        {
        "type": 0,  //类型，0,1,2,3,4，非必须
        "phone": "13000082001"
        }
     * </pre>
     */
    @PostMapping("gets/verify")
    public Map<String, Object> getVerify(@RequestBody String request) {
        GsonMap<String, Object> requestObject = null;
        int type;
        String phone;
        try {
            requestObject = GsonMap.of(GsonParser.parseRequest(request));
            type = requestObject.getIntValue(TYPE);
            phone = requestObject.getString(PHONE);
        } catch (Exception e) {
            return extendErrorResult(requestObject, e);
        }
        return new GsonParser(GETS, false).parseResponse(newVerifyRequest(type, phone));
    }

    /**校验验证码
     * @param request
     * <pre>
        {
        "type": 0,  //类型，0,1,2,3,4，非必须
        "phone": "13000082001",
        "verify": "123456"
        }
     * </pre>
     */
    @PostMapping("heads/verify")
    public Map<String, Object> headVerify(@RequestBody String request) {
        GsonMap<String, Object> requestObject = null;
        int type;
        String phone;
        String verify;
        try {
            requestObject = GsonMap.of(GsonParser.parseRequest(request));
            type = requestObject.getIntValue(TYPE);
            phone = requestObject.getString(PHONE);
            verify = requestObject.getString(VERIFY);
        } catch (Exception e) {
            return extendErrorResult(requestObject, e);
        }
        return headVerify(type, phone, verify);
    }

    /**
     * 校验验证码
     */
    public Map<String, Object> headVerify(int type, String phone, String code) {
        JSONResponse response = new JSONResponse(
                new GsonParser(GETS, false).parseResponse(
                        newVerifyRequest(type, phone, code)
                )
        );
        Verify verify = response.getObject(Verify.class);
        if (verify == null) {
            Exception e = StringUtil.isEmpty(code, true)
                    ? new NotExistException("验证码不存在！") : new ConditionErrorException("手机号或验证码错误！");
            return newErrorResult(e);
        }

        //验证码过期
        long time = BaseModel.getTimeMillis(verify.getDate());
        long now = System.currentTimeMillis();
        if (now > 60*1000 + time) {
            new GsonParser(DELETE, false).parseResponse(
                    newVerifyRequest(type, phone)
            );
            return newErrorResult(new TimeoutException("验证码已过期！"));
        }

        return new GsonParser(HEADS, false).parseResponse(
                newVerifyRequest(type, phone, code)
        );
    }



    /**
     * 新建一个验证码请求
     */
    public static Map<String, Object> newVerifyRequest(int type, String phone) {
        return newVerifyRequest(type, phone, null);
    }

    /**
     * 新建一个验证码请求
     */
    public static Map<String, Object> newVerifyRequest(int type, String phone, String verify) {
        return new JSONRequest(
                new Verify(type, phone).setVerify(verify)
        ).setTag(VERIFY_).setFormat(true);
    }

    /**用户登录
     * @param request 只用String，避免encode后未decode
     * @return
     * <pre>
    {
    "type": 0,  //登录方式，非必须  0-密码 1-验证码
    "phone": "13000082001",
    "password": "1234567",
    "version": 1 //全局版本号，非必须
    }
     * </pre>
     */
    @PostMapping(LOGIN)  //TODO 改 SQLConfig<T, M, L> 里的 dbAccount, dbPassword，直接用数据库鉴权
    public Map<String, Object> login(@RequestBody String request, HttpSession session) {
        GsonMap<String, Object> requestObject = null;
        boolean isPassword;
        String phone;
        String password;
        int version;
        Boolean format;
        boolean remember;
        Boolean asDBAccount;
        Map<String, Object> defaults;
        try {
            requestObject = GsonMap.of(GsonParser.parseRequest(request));

            isPassword = requestObject.getIntValue(TYPE) == LOGIN_TYPE_PASSWORD;//登录方式
            phone = requestObject.getString(PHONE);//手机
            password = requestObject.getString(PASSWORD);//密码

            if (!StringUtil.isPhone(phone)) {
                throw new IllegalArgumentException("手机号不合法！");
            }

            if (isPassword) {
                if (!StringUtil.isPassword(password)) {
                    throw new IllegalArgumentException("密码不合法！");
                }
            } else {
                if (!StringUtil.isVerify(password)) {
                    throw new IllegalArgumentException("验证码不合法！");
                }
            }

            version = requestObject.getIntValue(VERSION);
            format = requestObject.getBoolean(FORMAT);
            remember = requestObject.getBooleanValue(REMEMBER);
            asDBAccount = requestObject.getBoolean(AS_DB_ACCOUNT);
            defaults = requestObject.getJSONObject(DEFAULTS); //默认加到每个请求最外层的字段
            requestObject.remove(VERSION);
            requestObject.remove(FORMAT);
            requestObject.remove(REMEMBER);
            requestObject.remove(DEFAULTS);
        } catch (Exception e) {
            return extendErrorResult(requestObject, e);
        }


        //手机号是否已注册
        GsonMap<String, Object> phoneResponse = GsonMap.of(new GsonParser(HEADS, false).parseResponse(
                new JSONRequest(new Privacy().setPhone(phone)))
        );
        if (!JSONResponse.isSuccess(phoneResponse)) {
            return newResult(phoneResponse.getIntValue(JSONResponse.KEY_CODE), getString(phoneResponse, JSONResponse.KEY_MSG));
        }
        JSONResponse response = new JSONResponse(phoneResponse).getJSONResponse(PRIVACY_);
        if(!JSONResponse.isExist(response)) {
            return newErrorResult(new NotExistException("手机号未注册"));
        }

        //根据phone获取User
        Map<String, Object> privacyResponse = new GsonParser(GETS, false).parseResponse(
                new JSONRequest(
                        new Privacy().setPhone(phone)
                ).setFormat(true)
        );
        response = new JSONResponse(privacyResponse);

        Privacy privacy = response.getObject(Privacy.class);
        long userId = privacy == null ? 0 : BaseModel.value(privacy.getId());
        if (userId <= 0) {
            return privacyResponse;
        }

        //校验凭证
        if (isPassword) { //password 密码登录
            response = new JSONResponse(
                    new GsonParser(HEADS, false).parseResponse(
                            new JSONRequest(new Privacy(password).setId(userId))
                    )
            );
        } else {//verify手机验证码登录
            response = new JSONResponse(headVerify(Verify.TYPE_LOGIN, phone, password));
        }
        if (!JSONResponse.isSuccess(response)) {
            return response.toObject(Map.class);
        }
        response = response.getJSONResponse(isPassword ? PRIVACY_ : VERIFY_);
        if (!JSONResponse.isExist(response)) {
            return newErrorResult(new ConditionErrorException("账号或密码错误"));
        }

        response = new JSONResponse(
                new GsonParser(GETS, false).parseResponse(
                        new JSONRequest(  // 兼容 MySQL 5.6 及以下等不支持 json 类型的数据库
                                USER_,  // User 里在 setContactIdList(List<Long>) 后加 setContactIdList(String) 没用
                                JSONRequest.setJson(JSON.parseObject(new User(userId)),"contactIdList,pictureList")
                        )
                                .setFormat(true)
                )
        );
        User user = response.getObject(User.class);
        if (user == null || BaseModel.value(user.getId()) != userId) {
            return newErrorResult(new NullPointerException("服务器内部错误"));
        }

        //登录状态保存至session
        super.login(session, user, version, format, defaults);
        session.setAttribute(USER_ID, userId); //用户id
        session.setAttribute(TYPE, isPassword ? LOGIN_TYPE_PASSWORD : LOGIN_TYPE_VERIFY); //登录方式
        session.setAttribute(USER_, user); //用户
        session.setAttribute(PRIVACY_, privacy); //用户隐私信息
        session.setAttribute(REMEMBER, remember); //是否记住登录
        session.setAttribute(AS_DB_ACCOUNT, asDBAccount); //是否作为数据库账号密码
        session.setMaxInactiveInterval(60*60*24*(remember ? 7 : 1)); //设置session过期时间

        response.put(REMEMBER, remember);
        response.put(DEFAULTS, defaults);
        return response;
    }

    /**
     * 退出登录，清空session
     */
    @PostMapping("logout")
    @Override
    public Map<String, Object> logout(HttpSession session) {
        Long userId;
        try {
            userId = GsonVerifier.getVisitorId(session);//必须在session.invalidate();前！
            Log.d(TAG, "logout  userId = " + userId + "; session.getId() = " + (session == null ? null : session.getId()));
            super.logout(session);
        } catch (Exception e) {
            return newErrorResult(e);
        }

        Map<String, Object> result = newSuccessResult();
        Map<String, Object> user = newSuccessResult();
        user.put(ID, userId);
        user.put(COUNT, 1);
        result.put(StringUtil.firstCase(USER_), user);

        return result;
    }

    /**注册
     * @param request 只用String，避免encode后未decode
     * @return
     * <pre>
    {
    "Privacy": {
    "phone": "13000082222",
    "_password": "123456"
    },
    "User": {
    "name": "APIJSONUser"
    },
    "verify": "1234"
    }
     * </pre>
     */
    @PostMapping(REGISTER)
    public Map<String, Object> register(@RequestBody String request) {
        GsonMap<String, Object> requestObject = null;

        GsonMap<String, Object> privacyObj;

        String phone;
        String verify;
        String password;
        try {
            requestObject = GsonMap.of(GsonParser.parseRequest(request));
            privacyObj = GsonMap.of(requestObject.getJSONObject(PRIVACY_));

            phone = StringUtil.get(privacyObj.getString(PHONE));
            verify = requestObject.getString(VERIFY);
            password = privacyObj.getString(_PASSWORD);

            if (!StringUtil.isPhone(phone)) {
                return newIllegalArgumentResult(requestObject, PRIVACY_ + "/" + PHONE);
            }
            if (!StringUtil.isPassword(password)) {
                return newIllegalArgumentResult(requestObject, PRIVACY_ + "/" + _PASSWORD);
            }
            if (!StringUtil.isVerify(verify)) {
                return newIllegalArgumentResult(requestObject, VERIFY);
            }
        } catch (Exception e) {
            return extendErrorResult(requestObject, e);
        }


        JSONResponse response = new JSONResponse(headVerify(Verify.TYPE_REGISTER, phone, verify));
        if (!JSONResponse.isSuccess(response)) {
            return response;
        }
        //手机号或验证码错误
        if (!JSONResponse.isExist(response.getJSONResponse(VERIFY_))) {
            return extendErrorResult(response, new ConditionErrorException("手机号或验证码错误！"));
        }

        //生成User和Privacy
        if (StringUtil.isEmpty(requestObject.getString(JSONRequest.KEY_TAG), true)) {
            requestObject.put(JSONRequest.KEY_TAG, REGISTER);
        }
        requestObject.put(JSONRequest.KEY_FORMAT, true);
        response = new JSONResponse(
                new GsonParser(POST).setNeedVerifyLogin(false).parseResponse(requestObject)
        );

        //验证User和Privacy
        User user = response.getObject(User.class);
        long userId = user == null ? 0 : BaseModel.value(user.getId());
        Privacy privacy = response.getObject(Privacy.class);
        long userId2 = privacy == null ? 0 : BaseModel.value(privacy.getId());
        Exception e = null;
        if (userId <= 0 || userId != userId2) { //id不同
            e = new Exception("服务器内部错误！写入User或Privacy失败！");
        }

        if (e != null) { //出现错误，回退
            new GsonParser(DELETE, false).parseResponse(
                    new JSONRequest(new User(userId))
            );
            new GsonParser(DELETE, false).parseResponse(
                    new JSONRequest(new Privacy(userId2))
            );
        }

        return response;
    }


    public static Map<String, Object> newIllegalArgumentResult(Map<String, Object> requestObject, String key) {
        return newIllegalArgumentResult(requestObject, key, null);
    }

    public static Map<String, Object> newIllegalArgumentResult(Map<String, Object> requestObject, String key, String msg) {
        return new GsonParser().extendErrorResult(requestObject, new IllegalArgumentException(key + ":value 中value不合法！" + StringUtil.get(msg)));
    }


    /**设置密码
     * @param request 只用String，避免encode后未decode
     * @return
     * <pre>
    使用旧密码修改
    {
    "oldPassword": 123456,
    "Privacy":{
    "id": 13000082001,
    "_password": "1234567"
    }
    }
    或使用手机号+验证码修改
    {
    "verify": "1234",
    "Privacy":{
    "phone": "13000082001",
    "_password": "1234567"
    }
    }
     * </pre>
     */
    @PostMapping("put/password")
    public Map<String, Object> putPassword(@RequestBody String request){
        GsonMap<String, Object> requestObject = null;
        String oldPassword;
        String verify;

        int type = Verify.TYPE_PASSWORD;

        GsonMap<String, Object> privacyObj;
        long userId;
        String phone;
        String password;
        try {
            requestObject = GsonMap.of(GsonParser.parseRequest(request));
            oldPassword = StringUtil.get(requestObject.getString(OLD_PASSWORD));
            verify = StringUtil.get(requestObject.getString(VERIFY));

            requestObject.remove(OLD_PASSWORD);
            requestObject.remove(VERIFY);

            privacyObj = GsonMap.of(requestObject.getJSONObject(PRIVACY_));
            if (privacyObj == null) {
                throw new IllegalArgumentException(PRIVACY_ + " 不能为空！");
            }
            userId = privacyObj.getLongValue(ID);
            phone = privacyObj.getString(PHONE);
            password = privacyObj.getString(_PASSWORD);

            if (StringUtil.isEmpty(password, true)) { //支付密码
                type = Verify.TYPE_PAY_PASSWORD;
                password = privacyObj.getString(_PAY_PASSWORD);
                if (!StringUtil.isNumberPassword(password)) {
                    throw new IllegalArgumentException(PRIVACY_ + "/" + _PAY_PASSWORD + ":value 中value不合法！");
                }
            } else { //登录密码
                if (!StringUtil.isPassword(password)) {
                    throw new IllegalArgumentException(PRIVACY_ + "/" + _PASSWORD + ":value 中value不合法！");
                }
            }
        } catch (Exception e) {
            return extendErrorResult(requestObject, e);
        }


        if (StringUtil.isPassword(oldPassword)) {
            if (userId <= 0) { //手机号+验证码不需要userId
                return extendErrorResult(requestObject, new IllegalArgumentException(ID + ":value 中value不合法！"));
            }
            if (oldPassword.equals(password)) {
                return extendErrorResult(requestObject, new ConflictException("新旧密码不能一样！"));
            }

            //验证旧密码
            Privacy privacy = new Privacy(userId);
            if (type == Verify.TYPE_PASSWORD) {
                privacy.setPassword(oldPassword);
            } else {
                privacy.setPayPassword(oldPassword);
            }
            JSONResponse response = new JSONResponse(
                    new GsonParser(HEAD, false).parseResponse(
                            new JSONRequest(privacy).setFormat(true)
                    )
            );
            if (!JSONResponse.isExist(response.getJSONResponse(PRIVACY_))) {
                return extendErrorResult(requestObject, new ConditionErrorException("账号或原密码错误，请重新输入！"));
            }
        }
        else if (StringUtil.isPhone(phone) && StringUtil.isVerify(verify)) {
            JSONResponse response = new JSONResponse(headVerify(type, phone, verify));
            if (!JSONResponse.isSuccess(response)) {
                return response;
            }
            if (!JSONResponse.isExist(response.getJSONResponse(VERIFY_))) {
                return extendErrorResult(response, new ConditionErrorException("手机号或验证码错误！"));
            }
            response = new JSONResponse(
                    new GsonParser(GET, false).parseResponse(
                            new JSONRequest(
                                    new Privacy().setPhone(phone)
                            )
                    )
            );
            Privacy privacy = response.getObject(Privacy.class);
            long id = privacy == null ? 0 : BaseModel.value(privacy.getId());
            privacyObj.remove(PHONE);
            privacyObj.put(ID, id);

            requestObject.put(PRIVACY_, privacyObj);
        } else {
            return extendErrorResult(requestObject, new IllegalArgumentException("请输入合法的 旧密码 或 手机号+验证码 ！"));
        }
        //TODO 上线版加上   password = MD5Util.MD5(password);


        //requestObject.put(JSONRequest.KEY_TAG, "Password");
        requestObject.put(JSONRequest.KEY_FORMAT, true);
        //修改密码
        return new GsonParser(PUT, false).parseResponse(requestObject);
    }
}
