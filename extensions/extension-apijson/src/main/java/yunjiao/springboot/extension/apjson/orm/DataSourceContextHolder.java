package yunjiao.springboot.extension.apjson.orm;

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
        if(!StringUtils.hasText(dsType) || "null".equals(dsType)) {
            dsType = DEFAULT;
        }
        contextHolder.set(dsType);
    }

    public static String getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}
