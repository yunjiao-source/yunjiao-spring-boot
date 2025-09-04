package yunjiao.springboot.extension.apijson.gson;

import apijson.gson.APIJSONSQLConfig;
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

    private final String dbVersion;

    @Override
    public String gainDBVersion() {
        return dbVersion;
    }
}
