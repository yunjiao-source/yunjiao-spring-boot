package io.yunjiao.extension.apjson.orm;

import apijson.JSONMap;

/**
 * APIJSON默认策略
 *
 * @author yangyunjiao
 */
public class IdKeyApijsonStrategy implements IdKeyStrategy{
    @Override
    public String getIdKey(String database, String schema, String datasource, String table) {
        return JSONMap.KEY_ID;
    }
}
