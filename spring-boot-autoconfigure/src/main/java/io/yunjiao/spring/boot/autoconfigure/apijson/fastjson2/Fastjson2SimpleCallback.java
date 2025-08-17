package io.yunjiao.spring.boot.autoconfigure.apijson.fastjson2;

import apijson.RequestMethod;
import apijson.fastjson2.APIJSONApplication;
import apijson.orm.AbstractSQLConfig;
import apijson.orm.SQLConfig;
import io.yunjiao.spring.apijson.orm.IdKeyStrategy;
import io.yunjiao.spring.apijson.orm.NewIdStrategy;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * {@link AbstractSQLConfig.SimpleCallback} 子类，fastjson2回调类
 *
 * @author yangyunjiao
 * @see AbstractSQLConfig.Callback
 */
@RequiredArgsConstructor
public class Fastjson2SimpleCallback extends AbstractSQLConfig.SimpleCallback<Serializable, Map<String, Object>, List<Object>> {
    /**
     * {@link IdKeyStrategy} 实例注入
     */
    private final IdKeyStrategy idKeyStrategy;

    /**
     * {@link NewIdStrategy} 实例注入
     */
    private final NewIdStrategy newIdStrategy;

    @Override
    public Serializable newId(RequestMethod method, String database, String schema, String datasource, String table) {
        return newIdStrategy.newId(method, database, schema, datasource, table);
    }

    @Override
    public String getIdKey(String database, String schema, String datasource, String table) {
        return idKeyStrategy.getIdKey(database, schema, datasource, table);
    }

    @Override
    public SQLConfig<Serializable, Map<String, Object>, List<Object>> getSQLConfig(RequestMethod method, String database, String schema, String datasource, String table) {
        SQLConfig<Serializable, Map<String, Object>, List<Object>> config = APIJSONApplication.createSQLConfig();
        config.setMethod(method);
        config.setDatabase(database);
        config.setDatasource(datasource);
        config.setSchema(schema);
        config.setTable(table);
        return config;
    }
}
