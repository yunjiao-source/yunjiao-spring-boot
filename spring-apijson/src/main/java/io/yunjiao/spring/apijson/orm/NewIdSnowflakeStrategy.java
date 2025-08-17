package io.yunjiao.spring.apijson.orm;

import apijson.RequestMethod;
import cn.hutool.core.lang.Snowflake;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * 雪花算法主键生成
 *
 * @author yangyunjiao
 * @see NewIdStrategy
 */
@RequiredArgsConstructor
public class NewIdSnowflakeStrategy implements NewIdStrategy {
    private final Snowflake snowflake;

    @Override
    public Serializable newId(RequestMethod method, String database, String schema, String datasource, String table) {
        return snowflake.nextId();
    }
}
