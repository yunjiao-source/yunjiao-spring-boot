package io.yunjiao.springboot.autoconfigure.apijson.gson;

import apijson.gson.APIJSONSQLExecutor;
import apijson.orm.SQLConfig;
import io.yunjiao.extension.apjson.orm.SqlConnectProvider;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * SQL执行器 {@link APIJSONSQLExecutor} 子类， 默认实现。 从数据源{@link DataSource }获取连接
 *
 * @author yangyunjiao
 */
@RequiredArgsConstructor
public class GsonSqlExecutor extends APIJSONSQLExecutor<Serializable> implements SqlConnectProvider {
    /**
     * 标识
     */
    public static final  String TAG = GsonSqlExecutor.class.getSimpleName();

    private final DataSource dataSource;

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Connection getConnection(SQLConfig<Serializable, Map<String, Object>, List<Object>> config) throws Exception {
        String key = getConnectionKey(config);
        getConnectionFromDataSource(key);
        return super.getConnection(config);
    }
}
