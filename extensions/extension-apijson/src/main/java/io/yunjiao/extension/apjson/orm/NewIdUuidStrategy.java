package io.yunjiao.extension.apjson.orm;

import apijson.RequestMethod;
import cn.hutool.core.util.IdUtil;

import java.io.Serializable;

/**
 * UUID主键生成
 *
 * @author yangyunjiao
 * @see NewIdStrategy
 */
public class NewIdUuidStrategy implements NewIdStrategy {

    @Override
    public Serializable newId(RequestMethod method, String database, String schema, String datasource, String table) {
        return IdUtil.simpleUUID();
    }
}
