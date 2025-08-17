package io.yunjiao.spring.boot.autoconfigure.apijson.fastjson2;

import apijson.fastjson2.APIJSONSQLConfig;
import io.yunjiao.spring.boot.autoconfigure.apijson.ApijsonSqlProperties;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * SQL配置 {@link apijson.framework.APIJSONSQLConfig} 子类， 默认实现
 *
 * @author yangyunjiao
 */
@RequiredArgsConstructor
public class Fastjson2SqlConfig extends APIJSONSQLConfig<Serializable> {
    /**
     * 标识
     */
    public static final  String TAG = Fastjson2SqlConfig.class.getSimpleName();

    private final ApijsonSqlProperties properties;

    @Override
    public String gainDBVersion() {
        return properties.getConfig().getVersion();
    }

}
