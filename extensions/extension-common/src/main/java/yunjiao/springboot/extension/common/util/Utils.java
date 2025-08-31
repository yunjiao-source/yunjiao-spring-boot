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
     * @param targetClass 目标类型
     * @param defaultValue 默认值
     * @return 变量值
     * @param <T> 类型
     */
    public static <T extends Number> T convertEnv(Environment env, String name, Class<T> targetClass, T defaultValue) {
        // 从Environment对象中获取环境变量值
        String value = env.getProperty(name);

        // 如果环境变量不存在或为空，返回默认值
        if (!StringUtils.hasText(value)) {
            return defaultValue;
        }

        try {
            // 根据目标类型进行转换
            if (targetClass == Integer.class) {
                return targetClass.cast(Integer.parseInt(value));
            } else if (targetClass == Double.class) {
                return targetClass.cast(Double.parseDouble(value));
            } else if (targetClass == Float.class) {
                return targetClass.cast(Float.parseFloat(value));
            } else if (targetClass == Long.class) {
                return targetClass.cast(Long.parseLong(value));
            } else if (targetClass == Short.class) {
                return targetClass.cast(Short.parseShort(value));
            } else if (targetClass == Byte.class) {
                return targetClass.cast(Byte.parseByte(value));
            } else {
                throw new IllegalArgumentException("Unsupported number type: " + targetClass.getName());
            }
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
