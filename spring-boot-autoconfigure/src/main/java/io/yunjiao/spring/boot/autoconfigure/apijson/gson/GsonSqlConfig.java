package io.yunjiao.spring.boot.autoconfigure.apijson.gson;

import apijson.gson.APIJSONSQLConfig;
import io.yunjiao.spring.boot.autoconfigure.apijson.ApijsonSqlProperties;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * SQL配置 {@link apijson.framework.APIJSONSQLConfig} 子类， 默认实现
 *
 * @author yangyunjiao
 */
@RequiredArgsConstructor
public class GsonSqlConfig extends APIJSONSQLConfig<Serializable> {
    /**
     * 标识
     */
    public static final  String TAG = GsonSqlConfig.class.getSimpleName();

    private final ApijsonSqlProperties properties;

    @Override
    public String gainDBVersion() {
        return properties.getConfig().getVersion();
    }
}
