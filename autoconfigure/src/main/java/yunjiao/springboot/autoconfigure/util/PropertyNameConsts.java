package yunjiao.springboot.autoconfigure.util;

/**
 * 属性名称常量定义
 *
 * @author yangyunjiao
 */
public class PropertyNameConsts {
    /**
     * enabled属性
     */
    public static final String PROPERTY_ENABLED = ".enabled";

    /**
     * spring 属性
     */
    public static final String PROPERTY_PREFIX_SPRING = "spring";

    // id

    /**
     * id 属性
     */
    public static final String PROPERTY_PREFIX_ID = PROPERTY_PREFIX_SPRING + ".id";

    /**
     * Uid-Generator 属性
     */
    public static final String PROPERTY_PREFIX_ID_UIDGENERATOR = PROPERTY_PREFIX_ID + ".uid-generator";


    // captcha

    /**
     * 验证码属性
     */
    public static final String PROPERTY_PREFIX_CAPTCHA = PROPERTY_PREFIX_SPRING + ".captcha";

    /**
     * 验证码属性 Hutool
     */
    public static final String PROPERTY_PREFIX_CAPTCHA_HUTOOL = PROPERTY_PREFIX_CAPTCHA + ".hutool";

    /**
     * 验证码属性 Hutool
     */
    public static final String PROPERTY_PREFIX_CAPTCHA_ANJI = PROPERTY_PREFIX_CAPTCHA + ".anji";

    // APIJSON

    /**
     * apijson 属性
     */
    public static final String PROPERTY_PREFIX_APIJSON = PROPERTY_PREFIX_SPRING + ".apijson";

    /**
     * apijson rest-api 属性
     */
    public static final String PROPERTY_PREFIX_APIJSON_RESTAPI = PROPERTY_PREFIX_APIJSON + ".rest-api";

    /**
     * apijson rest-api enabled 属性
     */
    public static final String PROPERTY_APIJSON_RESTAPI_ENABLE = PROPERTY_PREFIX_APIJSON_RESTAPI + PROPERTY_ENABLED;

    /**
     * apijson sql 属性
     */
    public static final String PROPERTY_PREFIX_APIJSON_SQL = PROPERTY_PREFIX_APIJSON + ".sql";

    /**
     * apijson parser 属性
     */
    public static final String PROPERTY_PREFIX_APIJSON_PARSER = PROPERTY_PREFIX_APIJSON + ".parser";

    /**
     * apijson verifier 属性
     */
    public static final String PROPERTY_PREFIX_APIJSON_VERIFIER = PROPERTY_PREFIX_APIJSON + ".verifier";

    /**
     * apijson application 属性
     */
    public static final String PROPERTY_PREFIX_APIJSON_APPLICATION = PROPERTY_PREFIX_APIJSON + ".application";

    /**
     * apijson newidstrategy 属性
     */
    public static final String PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY = PROPERTY_PREFIX_APIJSON + ".new-id-strategy";

}
