package io.yunjiao.spring.apijson.orm;

import apijson.RequestMethod;

import java.io.Serializable;

/**
 * 主键自动生成，依赖数据库功能
 *
 * @author yangyunjiao
 * @see NewIdStrategy
 */
public class NewIdDatabaseStrategy implements NewIdStrategy {
    @Override
    public Serializable newId(RequestMethod method, String database, String schema, String datasource, String table) {
        return null;
    }
}
