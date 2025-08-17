package io.yunjiao.spring.apijson.orm;

import apijson.RequestMethod;

import java.io.Serializable;

/**
 * 主键自动生成。
 *
 * @author yangyunjiao
 * @see NewIdStrategy
 */
public class NewIdExceptionStrategy implements NewIdStrategy {
    @Override
    public Serializable newId(RequestMethod method, String database, String schema, String datasource, String table) {
        throw new UnsupportedOperationException("未配置NewIdStrategy子类");
    }
}
