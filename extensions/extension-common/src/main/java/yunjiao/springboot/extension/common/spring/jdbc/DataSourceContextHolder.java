package yunjiao.springboot.extension.common.spring.jdbc;

import org.springframework.util.StringUtils;

/**
 * ThreadLocal保存数据源标识
 *
 * @author yangyunjiao
 */
public class DataSourceContextHolder {
    public static final String DEFAULT = "default";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSourceType(String dsType) {
        if (!StringUtils.hasText(dsType)) {
            contextHolder.set(DEFAULT);
        } else {
            contextHolder.set(dsType);
        }

    }

    public static String getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}
