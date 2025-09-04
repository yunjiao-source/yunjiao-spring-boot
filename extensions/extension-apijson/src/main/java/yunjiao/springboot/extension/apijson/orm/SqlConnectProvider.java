package yunjiao.springboot.extension.apijson.orm;

import apijson.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import yunjiao.springboot.extension.common.spring.jdbc.DataSourceContextHolder;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * sql 连接提供者
 *
 * @author yangyunjiao
 */
public interface SqlConnectProvider {
    /**
     * 日志工具
     */
    Logger log = LoggerFactory.getLogger(SqlConnectProvider.class);

    /**
     * 解析key，返回datasource名称。如果返回失败， 返回null
     *
     * 注意：APIJSON框架参数错位，第三个参数是datasource
     *
     * @param key 格式：database + "-" + datasource + "-" + namespace + "-" + catalog
     * @return 返回datasource
     */
    default String parseKey(String key) {
        if (!StringUtils.hasText(key)) {
            return null;
        }

        String[] parts = key.split("-");
        if (parts.length >= 3) {
            return parts[2];
        }

        return null;
    }

    /**
     * 从数据源中获取链接
     * @param key 关键字
     * @throws Exception 获取失败
     */
    default void getConnectionFromDataSource(String key) throws Exception {
        Connection c = getConnection(key);
        if (c == null || c.isClosed()) {
            synchronized (this) {
                c = getConnection(key);
                if (c == null || c.isClosed()) {
                    Connection newCon = null;
                    try {
                        String dataSourceType = parseKey(key);
                        DataSourceContextHolder.setDataSourceName(dataSourceType);
                        DataSource dataSource = getDataSource();
                        newCon = dataSource.getConnection();
                    }finally {
                        DataSourceContextHolder.clearDataSourceName();
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("Successfully obtained a connection from data source: {}->{}", key, newCon);
                    }
                    putConnection(key, newCon);
                }
            }
        }
    }

    /**
     * 获取链接
     *
     * @param key 关键字，必须值
     * @return 连接
     * @throws Exception 获取时异常
     */
    @NotNull
    Connection getConnection(String key) throws Exception;

    /**
     * 放入链接
     *
     * @param key 关键字，必须值
     * @param newCon 连接
     * @return 连接
     * @throws Exception 放入时异常
     */
    Connection putConnection(String key, Connection newCon) throws Exception;

    /**
     * 获取数据源
     *
     * @return 实例
     */
    DataSource getDataSource();
}
