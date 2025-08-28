package yunjiao.springboot.autoconfigure.apijson;

import apijson.framework.APIJSONSQLConfig;
import apijson.framework.ColumnUtil;
import apijson.orm.AbstractSQLConfig;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link ApijsonSqlConfigConfigurer} 单元测试用例
 *
 * @author yangyunjiao
 */
public class ApijsonSqlConfigConfigurerTest {

    @Test
    void testDefault() {
        ApijsonSqlConfigConfigurer configurer = new ApijsonSqlConfigConfigurerDemo();
        ApijsonSqlProperties.Config config = new ApijsonSqlProperties.Config();
        config.setEnableColumnConfig(false);
        config.setDefaultDatabase("database");
        config.setDefaultCatalog("catalog");
        config.setDefaultSchema("schema");
        config.setDefaultNamespace("namespace");
        config.setVersion("version");
        ApijsonUtils.forceInit(APIJSONSQLConfig.class);
        ApijsonUtils.buildAPIJSONSQLConfigStatic(config, List.of(configurer));

        assertThat(APIJSONSQLConfig.DEFAULT_DATABASE).isEqualTo(config.getDefaultDatabase());
        assertThat(APIJSONSQLConfig.DEFAULT_SCHEMA).isEqualTo(config.getDefaultSchema());
        assertThat(APIJSONSQLConfig.DEFAULT_CATALOG).isEqualTo(config.getDefaultCatalog());
        assertThat(APIJSONSQLConfig.DEFAULT_NAMESPACE).isEqualTo(config.getDefaultNamespace());
        assertThat(APIJSONSQLConfig.ENABLE_COLUMN_CONFIG).isEqualTo(config.isEnableColumnConfig());

        assertThat(AbstractSQLConfig.RAW_MAP).containsEntry("rawMap", "rawMap1");
        assertThat(ColumnUtil.VERSIONED_TABLE_COLUMN_MAP).hasSize(1);
        assertThat(ColumnUtil.VERSIONED_TABLE_COLUMN_MAP.get(0)).containsKey("versionedTableColumnMap");
        assertThat(ColumnUtil.VERSIONED_KEY_COLUMN_MAP).hasSize(1);
        assertThat(ColumnUtil.VERSIONED_KEY_COLUMN_MAP.get(0)).containsKey("versionedKeyColumnMap");
    }

    static class ApijsonSqlConfigConfigurerDemo implements ApijsonSqlConfigConfigurer {

        @Override
        public void configure(Map<String, String> rawMap, Map<String, List<String>> versionedTableColumnMap, Map<String, Map<String, String>> versionedKeyColumnMap) {
            rawMap.put("rawMap", "rawMap1");
            versionedTableColumnMap.put("versionedTableColumnMap", List.of("versionedTableColumnMap1"));
            versionedKeyColumnMap.put("versionedKeyColumnMap", Map.of("versionedKeyColumnMap1", "versionedKeyColumnMap2"));
        }
    }
}
