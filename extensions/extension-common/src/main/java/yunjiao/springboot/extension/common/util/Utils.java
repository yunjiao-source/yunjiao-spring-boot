package yunjiao.springboot.extension.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * 工具
 *
 * @author yangyunjiao
 */
@Slf4j
public final class Utils {
    /**
     * 获取环境变量，并转换成数字。
     * @param env 必须值
     * @param name 变量名称
     * @return 变量值
     */
    public static <T extends Number> T convertEnv(Environment env, String name, Class<T> clazz, T defaultValue) {
        // 从Environment对象中获取环境变量值
        String value = env.getProperty(name);

        // 如果环境变量不存在或为空，返回默认值
        if (!StringUtils.hasText(value)) {
            return defaultValue;
        }

        try {
            // 根据目标类型进行转换
            if (clazz == Integer.class) {
                return clazz.cast(Integer.parseInt(value));
            } else if (clazz == Double.class) {
                return clazz.cast(Double.parseDouble(value));
            } else if (clazz == Float.class) {
                return clazz.cast(Float.parseFloat(value));
            } else if (clazz == Long.class) {
                return clazz.cast(Long.parseLong(value));
            } else if (clazz == Short.class) {
                return clazz.cast(Short.parseShort(value));
            } else if (clazz == Byte.class) {
                return clazz.cast(Byte.parseByte(value));
            } else {
                throw new IllegalArgumentException("Unsupported number type: " + clazz.getName());
            }
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
