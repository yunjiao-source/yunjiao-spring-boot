package io.yunjiao.spring.apijson.orm;

/**
 * 主键名称策略
 *
 * @author yangyunjiao
 */
public interface IdKeyStrategy {
    /**
     * 主键名称
     * @param database 数据库名
     * @param schema schema名
     * @param datasource 数据源名
     * @param table 表名
     * @return 主键名称
     * @see apijson.orm.AbstractSQLConfig.SimpleCallback#getIdKey(String, String, String, String)
     */
    String getIdKey(String database, String schema, String datasource, String table);
}
