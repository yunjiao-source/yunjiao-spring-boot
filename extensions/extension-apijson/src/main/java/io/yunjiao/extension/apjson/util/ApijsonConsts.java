package io.yunjiao.extension.apjson.util;

import apijson.orm.SQLConfig;

/**
 * 常量定义
 *
 * @author yangyunjiao
 */
public final class  ApijsonConsts {
    /**
     * 请求头
     */
    public static final String APIJSON_DELEGATE_ID = "Apijson-Delegate-Id";

    /**
     * 默认数据库
     */
    public static final String SQL_CONFIG_DEFAULT_DATABASE = SQLConfig.DATABASE_MYSQL;

    /**
     * 默认的schema
     */
    public static final String SQL_CONFIG_DEFAULT_SCHEMA = "sys";


    /**
     * sql运行器配置
     */
    public static final String SQL_EXECUTOR_KEY_RAW_LIST = "@RAW@LIST";

    /**
     * sql运行器配置
     */
    public static final String SQL_EXECUTOR_KEY_VICE_ITEM = "@VICE@ITEM";
}
