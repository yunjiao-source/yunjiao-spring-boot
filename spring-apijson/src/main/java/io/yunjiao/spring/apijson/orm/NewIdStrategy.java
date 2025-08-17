package io.yunjiao.spring.apijson.orm;

import apijson.RequestMethod;

import java.io.Serializable;

/**
 * 主键生成策略
 *
 * @author yangyunjiao
 */
public interface NewIdStrategy {
    /**
     * 主键生成
     *
     * @param method 方法名
     * @param database 数据库名
     * @param schema schema名
     * @param datasource 数据源名
     * @param table 表名
     * @return 主键
     * @see apijson.orm.AbstractSQLConfig.SimpleCallback#newId(RequestMethod, String, String, String, String)
     */
    Serializable newId(RequestMethod method, String database, String schema, String datasource, String table);
}
