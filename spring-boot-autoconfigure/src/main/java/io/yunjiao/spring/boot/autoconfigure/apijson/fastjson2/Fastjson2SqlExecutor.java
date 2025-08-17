package io.yunjiao.spring.boot.autoconfigure.apijson.fastjson2;

import apijson.fastjson2.APIJSONSQLExecutor;
import apijson.orm.SQLConfig;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.yunjiao.spring.apijson.orm.SqlConnectProvider;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;

/**
 * SQL执行器 {@link APIJSONSQLExecutor} 子类， 默认实现。 从数据源{@link DataSource }获取连接
 *
 * @author yangyunjiao
 */
@RequiredArgsConstructor
public class Fastjson2SqlExecutor extends APIJSONSQLExecutor<Serializable> implements SqlConnectProvider {
    /**
     * 标识
     */
    public static final  String TAG = Fastjson2SqlExecutor.class.getSimpleName();

    private final DataSource dataSource;

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Connection getConnection(SQLConfig<Serializable, JSONObject, JSONArray> config) throws Exception {
        String key = getConnectionKey(config);
        getConnectionFromDataSource(key);
        return super.getConnection(config);
    }


}
