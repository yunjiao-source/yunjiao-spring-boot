package io.yunjiao.springboot.autoconfigure.apijson.fastjson2;

import apijson.Log;
import apijson.StringUtil;
import apijson.fastjson2.APIJSONController;
import apijson.fastjson2.JSONResponse;
import apijson.framework.BaseModel;
import apijson.orm.exception.ConditionErrorException;
import apijson.orm.exception.ConflictException;
import apijson.orm.exception.NotExistException;
import com.alibaba.fastjson2.JSONObject;
import io.yunjiao.extension.apjson.annotation.ApijsonRest;
import io.yunjiao.springboot.autoconfigure.apijson.fastjson2.model.Privacy;
import io.yunjiao.springboot.autoconfigure.apijson.fastjson2.model.User;
import io.yunjiao.springboot.autoconfigure.apijson.fastjson2.model.Verify;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.rmi.ServerException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import static apijson.JSON.getString;
import static apijson.RequestMethod.*;
import static apijson.framework.APIJSONConstant.*;

/**
 * 扩展接口，包括：登录，注册，验证码等等
 *
 * @author yangyunjiao
 */
@Slf4j
@ApijsonRest
@RequiredArgsConstructor
@RestController
@RequestMapping("ext")
public class Fastjson2EXtRestController extends APIJSONController<Serializable> {
    /**
     * {@link User} 类名
     */
    public static final  String USER_;

    /**
     * {@link Privacy} 类名
     */
    public static final  String PRIVACY_;

    /**
     * {@link Verify} 类名. 加下划线后缀是为了避免 Verify 和 verify 都叫VERIFY，分不清
     */
    public static final  String VERIFY_;

    /**
     * 常量：手机
     */
    public static final  String PHONE = "phone";

    /**
     * 常量：密码
     */
    public static final  String PASSWORD = "password";

    /**
     * 常量：密码，下划线前缀
     */
    public static final  String _PASSWORD = "_password";

    /**
     * 常量：支付密码
     */
    public static final  String _PAY_PASSWORD = "_payPassword";

    /**
     * 常量：老密码
     */
    public static final  String OLD_PASSWORD = "oldPassword";

    /**
     * 常量：校验
     */
    public static final  String VERIFY = "verify";

    /**
     * 常量：类型
     */
    public static final  String TYPE = "type";

    /**
     * 常量：值
     */
    public static final  String VALUE = "value";

    /**
     * 常量：登陆
     */
    public static final  String LOGIN = "login";

    /**
     * 常量：数据库用户
     */
    public static final  String AS_DB_ACCOUNT = "asDBAccount";

    /**
     * 常量：记住我
     */
    public static final  String REMEMBER = "remember";

    /**
     * 常量：默认
     */
    public static final  String DEFAULTS = "defaults";

    /**
     * 常量：密码登录
     */
    public static final  int LOGIN_TYPE_PASSWORD = 0;

    /**
     * 常量：验证码登录
     */
    public static final  int LOGIN_TYPE_VERIFY = 1;

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
    public JSONObject reload(@RequestBody String request) {
        JSONObject requestObject = null;
        String type;
        JSONObject value;
        String phone;
        String verify;
        try {
            requestObject = Fastjson2Parser.parseRequest(request);
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

        JSONObject result = newSuccessResult();

        boolean reloadAll = StringUtil.isEmpty(type, true) || "ALL".equals(type);

        if (reloadAll || "ACCESS".equals(type)) {
            try {
                result.put(ACCESS_, Fastjson2Verifier.initAccess(false, null, value));
            } catch (ServerException e) {
                e.printStackTrace();
                result.put(ACCESS_, newErrorResult(e));
            }
        }

        if (reloadAll || "FUNCTION".equals(type)) {
            try {
                result.put(FUNCTION_, Fastjson2FunctionParser.init(false, null, value));
            } catch (ServerException e) {
                e.printStackTrace();
                result.put(FUNCTION_, newErrorResult(e));
            }
        }

        if (reloadAll || "REQUEST".equals(type)) {
            try {
                result.put(REQUEST_, Fastjson2Verifier.initRequest(false, null, value));
            } catch (ServerException e) {
                e.printStackTrace();
                result.put(REQUEST_, newErrorResult(e));
            }
        }

        return result;
    }

    /**生成验证码,修改为post请求
     * @param request 请求
     * @return 响应对象
     * <pre>
            {
            "type": 0,  //类型，0,1,2,3,4，非必须
            "phone": "13000082001"
            }
     * </pre>
     */
    @PostMapping("post/verify")
    public JSONObject postVerify(@RequestBody String request) {
        JSONObject requestObject = null;
        int type;
        String phone;
        try {
            requestObject = Fastjson2Parser.parseRequest(request);
            type = requestObject.getIntValue(TYPE);
            phone = requestObject.getString(PHONE);
        } catch (Exception e) {
            return extendErrorResult(requestObject, e);
        }

        new Fastjson2Parser(DELETE, false).parse(newVerifyRequest(type, phone));

        JSONObject response = new Fastjson2Parser(POST, false).parseResponse(
                newVerifyRequest(type, phone, "" + (new Random().nextInt(9999) + 1000))
        );


        if (!JSONResponse.isSuccess(response)) {
            new Fastjson2Parser(DELETE, false).parseResponse(newVerifyRequest(type, phone));
            return response;
        }

        //TODO 这里直接返回验证码，方便测试。实际上应该只返回成功信息，验证码通过短信发送
        JSONObject object = new JSONObject();
        object.put(TYPE, type);
        object.put(PHONE, phone);
        return getVerify(apijson.fastjson2.JSON.toJSONString(object));
    }

    /**获取验证码
     * @param request 请求，包含类型，手机号等
     * @return 响应对象
     * <pre>
        {
        "type": 0,  //类型，0,1,2,3,4，非必须
        "phone": "13000082001"
        }
     * </pre>
     */
    @PostMapping("gets/verify")
    public JSONObject getVerify(@RequestBody String request) {
        JSONObject requestObject = null;
        int type;
        String phone;
        try {
            requestObject = Fastjson2Parser.parseRequest(request);
            type = requestObject.getIntValue(TYPE);
            phone = requestObject.getString(PHONE);
        } catch (Exception e) {
            return extendErrorResult(requestObject, e);
        }
        return new Fastjson2Parser(GETS, false).parseResponse(newVerifyRequest(type, phone));
    }

    /**
     * 校验验证码
     * @param request 请求对象，包括类型，手机号，校验码
     * @return 响应对象
     * <pre>
        {
        "type": 0,  //类型，0,1,2,3,4，非必须
        "phone": "13000082001",
        "verify": "123456"
        }
     * </pre>
     */
    @PostMapping("heads/verify")
    public JSONObject headVerify(@RequestBody String request) {
        JSONObject requestObject = null;
        int type;
        String phone;
        String verify;
        try {
            requestObject = Fastjson2Parser.parseRequest(request);
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
     *
     * @param type 类型
     * @param phone 手机
     * @param code 验证码
     * @return 验证响应信息
     */
    public JSONObject headVerify(int type, String phone, String code) {
        JSONResponse response = new JSONResponse(
                new Fastjson2Parser(GETS, false).parseResponse(
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
            new Fastjson2Parser(DELETE, false).parseResponse(
                    newVerifyRequest(type, phone)
            );
            return newErrorResult(new TimeoutException("验证码已过期！"));
        }

        return new Fastjson2Parser(HEADS, false).parseResponse(
                newVerifyRequest(type, phone, code)
        );
    }

    /**
     * 新建一个验证码请求
     *
     * @param type 类型
     * @param phone 手机
     * @return 验证码响应对象
     */
    public static JSONObject newVerifyRequest(int type, String phone) {
        return newVerifyRequest(type, phone, null);
    }

    /**
     * 新建一个验证码请求
     *
     * @param type 类型
     * @param phone 手机号
     * @param verify 验证码
     * @return 验证码响应请求
     */
    public static JSONObject newVerifyRequest(int type, String phone, String verify) {
        return new apijson.fastjson2.JSONRequest(
                new Verify(type, phone).setVerify(verify)
        ).setTag(VERIFY_).setFormat(true);
    }

    /**
     * 用户登录
     * @param request 请求信息，包括类型，手机号，密码，版本等
     * @param session 会话对象
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
    public JSONObject login(@RequestBody String request, HttpSession session) {
        JSONObject requestObject = null;
        boolean isPassword;
        String phone;
        String password;
        int version;
        Boolean format;
        boolean remember;
        Boolean asDBAccount;
        JSONObject defaults;
        try {
            requestObject = Fastjson2Parser.parseRequest(request);

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
        JSONObject phoneResponse = new Fastjson2Parser(HEADS, false).parseResponse(
                new apijson.fastjson2.JSONRequest(new Privacy().setPhone(phone))
        );
        if (!JSONResponse.isSuccess(phoneResponse)) {
            return newResult(phoneResponse.getIntValue(JSONResponse.KEY_CODE), getString(phoneResponse, JSONResponse.KEY_MSG));
        }
        JSONResponse response = new JSONResponse(phoneResponse).getJSONResponse(PRIVACY_);
        if(!JSONResponse.isExist(response)) {
            return newErrorResult(new NotExistException("手机号未注册"));
        }

        //根据phone获取User
        JSONObject privacyResponse = new Fastjson2Parser(GETS, false).parseResponse(
                new apijson.fastjson2.JSONRequest(
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
                    new Fastjson2Parser(HEADS, false).parseResponse(
                            new apijson.fastjson2.JSONRequest(new Privacy(userId).setPassword(password))
                    )
            );
        } else {//verify手机验证码登录
            response = new JSONResponse(headVerify(Verify.TYPE_LOGIN, phone, password));
        }
        if (!JSONResponse.isSuccess(response)) {
            return response.toObject(JSONObject.class);
        }
        response = response.getJSONResponse(isPassword ? PRIVACY_ : VERIFY_);
        if (!JSONResponse.isExist(response)) {
            return newErrorResult(new ConditionErrorException("账号或密码错误"));
        }

        response = new JSONResponse(
                new Fastjson2Parser(GETS, false).parseResponse(
                        new apijson.fastjson2.JSONRequest(  // 兼容 MySQL 5.6 及以下等不支持 json 类型的数据库
                                USER_,  // User 里在 setContactIdList(List<Long>) 后加 setContactIdList(String) 没用
                                apijson.fastjson2.JSONRequest.setJson(apijson.fastjson2.JSON.parseObject(new User(userId)),"contactIdList,pictureList")
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
     *
     * @param session 会话对象
     * @return 登出响应信息
     */
    @PostMapping("logout")
    @Override
    public JSONObject logout(HttpSession session) {
        Long userId;
        try {
            userId = Fastjson2Verifier.getVisitorId(session);//必须在session.invalidate();前！
            Log.d(TAG, "logout  userId = " + userId + "; session.getId() = " + (session == null ? null : session.getId()));
            super.logout(session);
        } catch (Exception e) {
            return newErrorResult(e);
        }

        JSONObject result = newSuccessResult();
        JSONObject user = newSuccessResult();
        user.put(ID, userId);
        user.put(COUNT, 1);
        result.put(StringUtil.firstCase(USER_), user);

        return result;
    }

    /**
     * 注册
     *
     * @param request 注册信息
     * @return 注册响应对象
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
    public JSONObject register(@RequestBody String request) {
        JSONObject requestObject = null;

        JSONObject privacyObj;

        String phone;
        String verify;
        String password;
        try {
            requestObject = Fastjson2Parser.parseRequest(request);
            privacyObj = requestObject.getJSONObject(PRIVACY_);

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
        if (StringUtil.isEmpty(requestObject.getString(apijson.fastjson2.JSONRequest.KEY_TAG), true)) {
            requestObject.put(apijson.fastjson2.JSONRequest.KEY_TAG, REGISTER);
        }
        requestObject.put(apijson.fastjson2.JSONRequest.KEY_FORMAT, true);
        response = new JSONResponse(
                new Fastjson2Parser(POST).setNeedVerifyLogin(false).parseResponse(requestObject)
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
            new Fastjson2Parser(DELETE, false).parseResponse(
                    new apijson.fastjson2.JSONRequest(new User(userId))
            );
            new Fastjson2Parser(DELETE, false).parseResponse(
                    new apijson.fastjson2.JSONRequest(new Privacy(userId2))
            );
        }

        return response;
    }


    /**
     * 创建异常
     *
     * @param requestObject 请求对象
     * @param key 关键字
     * @return 异常对象
     */
    public static JSONObject newIllegalArgumentResult(JSONObject requestObject, String key) {
        return newIllegalArgumentResult(requestObject, key, null);
    }

    /**
     * 创建异常
     *
     * @param requestObject 请求对象
     * @param key 关键字
     * @param msg 消息
     * @return 异常对象
     */
    public static JSONObject newIllegalArgumentResult(JSONObject requestObject, String key, String msg) {
        return new Fastjson2Parser().extendErrorResult(requestObject, new IllegalArgumentException(key + ":value 中value不合法！" + StringUtil.get(msg)));
    }


    /**设置密码
     * @param request 只用String，避免encode后未decode
     * @return 响应信息
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
    public JSONObject putPassword(@RequestBody String request){
        JSONObject requestObject = null;
        String oldPassword;
        String verify;

        int type = Verify.TYPE_PASSWORD;

        JSONObject privacyObj;
        long userId;
        String phone;
        String password;
        try {
            requestObject = Fastjson2Parser.parseRequest(request);
            oldPassword = StringUtil.get(requestObject.getString(OLD_PASSWORD));
            verify = StringUtil.get(requestObject.getString(VERIFY));

            requestObject.remove(OLD_PASSWORD);
            requestObject.remove(VERIFY);

            privacyObj = requestObject.getJSONObject(PRIVACY_);
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
                    new Fastjson2Parser(HEAD, false).parseResponse(
                            new apijson.fastjson2.JSONRequest(privacy).setFormat(true)
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
                    new Fastjson2Parser(GET, false).parseResponse(
                            new apijson.fastjson2.JSONRequest(
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
        requestObject.put(apijson.fastjson2.JSONRequest.KEY_FORMAT, true);
        //修改密码
        return new Fastjson2Parser(PUT, false).parseResponse(requestObject);
    }
}
