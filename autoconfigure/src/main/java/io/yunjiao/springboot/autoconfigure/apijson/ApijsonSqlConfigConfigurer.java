package io.yunjiao.springboot.autoconfigure.apijson;

import java.util.List;
import java.util.Map;

/**
 * ApijsonSqlConfig 用户自定义配置
 *
 * @author yangyunjiao
 */
@FunctionalInterface
public interface ApijsonSqlConfigConfigurer {
    /**
     * 配置
     *
     * @param rawMap 自定义原始 SQL 片段
     * @param versionedTableColumnMap 带版本的表和字段一对多对应关系，用来做 反选字段 Map
     * @param versionedKeyColumnMap 带版本的 JSON key 和表字段一对一对应关系，用来做字段名映射 Map
     */
    void configure(Map<String, String> rawMap, Map<String, List<String>> versionedTableColumnMap,
                   Map<String, Map<String, String>> versionedKeyColumnMap);
}
