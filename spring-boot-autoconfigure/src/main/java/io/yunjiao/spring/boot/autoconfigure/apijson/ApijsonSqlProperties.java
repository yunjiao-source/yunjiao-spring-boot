package io.yunjiao.spring.boot.autoconfigure.apijson;

import io.yunjiao.spring.apijson.util.ApijsonConsts;
import io.yunjiao.spring.boot.autoconfigure.util.PropertyNameConsts;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * sql配置属性
 *
 * @author yangyunjiao
 */
@Data
@ConfigurationProperties(prefix = PropertyNameConsts.PROPERTY_PREFIX_APIJSON_SQL)
public class ApijsonSqlProperties {


    /**
     * Sql 配置器属性
     */
    @NestedConfigurationProperty
    private Config config = new Config();

    /**
     * Sql 执行器属性
     */
    @NestedConfigurationProperty
    private Executor executor = new Executor();

    /**
     * sql配置。 参考 {@link apijson.framework.APIJSONSQLConfig}
     */
    @Data
    public static class Config {
        /**
         * 支持 !key 反选字段 和 字段名映射， 默认false
         */
        private boolean enableColumnConfig = false;

        /**
         * 默认的数据库类型, 默认MYSQL
         */
        private String defaultDatabase = ApijsonConsts.SQL_CONFIG_DEFAULT_DATABASE;

        /**
         * 默认数据库名/模式，默认sys。 默认情况是 MySQL: sys, PostgreSQL: public, SQL Server: dbo
         */
        private String defaultSchema = ApijsonConsts.SQL_CONFIG_DEFAULT_SCHEMA;

        private String defaultNamespace;

        private String defaultCatalog;

        /**
         * 数据库版本, 默认'5.7.22'
         */
        private String version = "5.7.22";


        // TODO 其他属性配置
    }

    /**
     * sql执行器配置。 参考 {@link apijson.framework.APIJSONSQLExecutor}
     */
    @Data
    public static class Executor {
        /**
         * 是否返回 值为null的字段, 默认false
         */
        private boolean enableOutputNullColumn = false;

        /**
         * 避免和字段命名冲突，不用 $RAW@LIST$ 是因为 $ 会在 fastjson 内部转义，浪费性能
         */
        private String keyRawList = ApijsonConsts.SQL_EXECUTOR_KEY_RAW_LIST;

        /**
         * 避免和字段命名冲突，不用 $VICE@LIST$ 是因为 $ 会在 fastjson 内部转义，浪费性能
         */
        private String keyViceItem = ApijsonConsts.SQL_EXECUTOR_KEY_VICE_ITEM;

        // TODO 其他属性配置

    }
}
