package io.yunjiao.springboot.autoconfigure.util;

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

    /**
     * hutool 属性
     */
    public static final String PROPERTY_PREFIX_HUTOOL = PROPERTY_PREFIX_SPRING + ".hutool";

    // APIJSON

    /**
     * apijson 属性
     */
    public static final String PROPERTY_PREFIX_APIJSON = PROPERTY_PREFIX_SPRING + ".apijson";

    /**
     * rest-api 属性
     */
    public static final String PROPERTY_PREFIX_APIJSON_RESTAPI = PROPERTY_PREFIX_APIJSON + ".rest-api";

    /**
     * enabled 属性
     */
    public static final String PROPERTY_PREFIX_APIJSON_RESTAPI_ENABLE = PROPERTY_PREFIX_APIJSON_RESTAPI + PROPERTY_ENABLED;

    /**
     * sql 属性
     */
    public static final String PROPERTY_PREFIX_APIJSON_SQL = PROPERTY_PREFIX_APIJSON + ".sql";

    /**
     * parser 属性
     */
    public static final String PROPERTY_PREFIX_APIJSON_PARSER = PROPERTY_PREFIX_APIJSON + ".parser";

    /**
     * verifier 属性
     */
    public static final String PROPERTY_PREFIX_APIJSON_VERIFIER = PROPERTY_PREFIX_APIJSON + ".verifier";

    /**
     * application 属性
     */
    public static final String PROPERTY_PREFIX_APIJSON_APPLICATION = PROPERTY_PREFIX_APIJSON + ".application";

    /**
     * newidstrategy 属性
     */
    public static final String PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY = PROPERTY_PREFIX_APIJSON + ".new-id-strategy";

}
