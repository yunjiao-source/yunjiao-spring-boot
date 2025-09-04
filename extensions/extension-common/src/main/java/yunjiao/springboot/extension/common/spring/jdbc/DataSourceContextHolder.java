package yunjiao.springboot.extension.common.spring.jdbc;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * ThreadLocal保存数据源标识
 *
 * @author yangyunjiao
 */
public class DataSourceContextHolder {
    /**
     * 默认是数据源名称
     */
    public static final String DEFAULT = "default";

    /**
     * 数据源名称上下文
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 设置上下文数据源名称，如果是空，设置默认数据源名称
     * @param dsName 数据源名称
     */
    public static void setDataSourceName(@Nullable String dsName) {
        if (!StringUtils.hasText(dsName)) {
            contextHolder.set(DEFAULT);
        } else {
            contextHolder.set(dsName);
        }

    }

    /**
     * 获取上下文数据源名称
     * @return 名称
     */
    public static String getDataSourceName() {
        return contextHolder.get();
    }

    /**
     * 清理上下文数据源名称
     */
    public static void clearDataSourceName() {
        contextHolder.remove();
    }
}
