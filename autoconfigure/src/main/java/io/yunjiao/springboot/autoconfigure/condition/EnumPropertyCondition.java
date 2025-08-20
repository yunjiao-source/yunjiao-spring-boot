package io.yunjiao.springboot.autoconfigure.condition;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 基于枚举的{@link SpringBootCondition}抽象子类，实现枚举的条件判断
 *
 * @author yangyunjiao
 * @param <E> 枚举类
 * @see SpringBootCondition
 */
@Slf4j
public abstract class EnumPropertyCondition<E extends Enum<E>> extends SpringBootCondition {
    /**
     * 期望值
     */
    private final E expectedValue;

    /**
     * 值类型
     */
    private final Class<E> enumClass;


    /**
     * 属性名
     */
    private final String propertyKey;

    /**
     * 默认构造函数
     *
     * @param expectedValue 期望值
     * @param enumClass 值类型
     * @param propertyKey 属性名
     */
    protected EnumPropertyCondition(E expectedValue, Class<E> enumClass, String propertyKey) {
        this.expectedValue = expectedValue;
        this.enumClass = enumClass;
        this.propertyKey = propertyKey;
    }

    /**
     * 获取条件名称（用于日志和报告）
     *
     * @return 名称
     */
    protected String getConditionName() {
        return getClass().getSimpleName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context,
                                            AnnotatedTypeMetadata metadata) {

        ConditionMessage.Builder message = ConditionMessage.forCondition(
                getConditionName(), "(" + expectedValue + ")"
        );

        Environment env = context.getEnvironment();
        String rawValue = env.getProperty(propertyKey);

        // 1. 检查属性是否存在
        if (!StringUtils.hasText(rawValue)) {
            return ConditionOutcome.noMatch(
                    message.because("未配置属性 '" + propertyKey + "'")
            );
        }

        // 2. 转换为枚举
        E actualValue = parseEnumValue(rawValue);
        if (actualValue == null) {
            String validValues = Arrays.stream(enumClass.getEnumConstants())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            return ConditionOutcome.noMatch(
                    message.because("'" + rawValue + "' 不是有效值。有效值为: " + validValues)
            );
        }

        // 3. 比较枚举值
        if (Objects.equals(actualValue, expectedValue)) {
            return ConditionOutcome.match(
                    message.because("配置值 '" + actualValue + "' 匹配期望值")
            );
        }

        return ConditionOutcome.noMatch(
                message.because("配置值 '" + actualValue + "' 不匹配期望值 '" + expectedValue + "'")
        );
    }

    /**
     * 解析字符串为枚举值
     *
     * @param value 枚举值
     * @return 枚举对象
     */
    private E parseEnumValue(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }

        String normalized = value.trim();
        try {
            return Enum.valueOf(enumClass, normalized);
        } catch (IllegalArgumentException e) {
            log.error("转换枚举时异常: {}", e.getMessage());
            return null;
        }
    }
}
