package io.yunjiao.springboot.autoconfigure.util;

/**
 * 属性名称常量定义
 *
 * @author yangyunjiao
 */
public class PropertyNameConsts {
    public static final String PROPERTY_ENABLED = ".enabled";

    public static final String PROPERTY_PREFIX_SPRING = "spring";

    public static final String PROPERTY_PREFIX_HUTOOL = PROPERTY_PREFIX_SPRING + ".hutool";

    // APIJSON

    public static final String PROPERTY_PREFIX_APIJSON = PROPERTY_PREFIX_SPRING + ".apijson";

    public static final String PROPERTY_PREFIX_APIJSON_RESTAPI = PROPERTY_PREFIX_APIJSON + ".rest-api";

    public static final String PROPERTY_PREFIX_APIJSON_RESTAPI_ENABLE = PROPERTY_PREFIX_APIJSON_RESTAPI + PROPERTY_ENABLED;

    public static final String PROPERTY_PREFIX_APIJSON_SQL = PROPERTY_PREFIX_APIJSON + ".sql";

    public static final String PROPERTY_PREFIX_APIJSON_PARSER = PROPERTY_PREFIX_APIJSON + ".parser";

    public static final String PROPERTY_PREFIX_APIJSON_VERIFIER = PROPERTY_PREFIX_APIJSON + ".verifier";

    public static final String PROPERTY_PREFIX_APIJSON_APPLICATION = PROPERTY_PREFIX_APIJSON + ".application";

    public static final String PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY = PROPERTY_PREFIX_APIJSON + ".new-id-strategy";

}
