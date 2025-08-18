package io.yunjiao.springboot.autoconfigure.condition;

import io.yunjiao.springboot.autoconfigure.test.TestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.mock.env.MockEnvironment;

/**
 * {@link EnumPropertyCondition} 单元测试用例
 *
 * @author yangyunjiao
 */
@ExtendWith(MockitoExtension.class)
public class EnumPropertyConditionTest {
    public static final  String PROPERTY_NAME = "spring.gender";

    @Mock
    private AnnotatedTypeMetadata metadata;

    @Test
    void giveCorrectValue_thenMatched() {
        // 准备测试环境
        MockEnvironment env = new MockEnvironment();
        env.setProperty(PROPERTY_NAME, "female");

        ConditionContext context = TestUtils.createMockContext(env);

        // 创建条件实例
        GenderCondition condition = new GenderCondition(Gender.female);

        // 执行测试
        ConditionOutcome outcome = condition.getMatchOutcome(context, metadata);

        // 验证结果
        Assertions.assertThat(outcome.isMatch()).isTrue();
        Assertions.assertThat(outcome.getMessage()).contains("配置值 'female' 匹配期望值");
    }

    @Test
    void giveWrongValue_thenNotMatched() {
        // 准备测试环境
        MockEnvironment env = new MockEnvironment();
        env.setProperty(PROPERTY_NAME, "Female");

        ConditionContext context = TestUtils.createMockContext(env);

        // 创建条件实例
        GenderCondition condition = new GenderCondition(Gender.female);

        // 执行测试
        ConditionOutcome outcome = condition.getMatchOutcome(context, metadata);

        // 验证结果
        Assertions.assertThat(outcome.isMatch()).isFalse();
        Assertions.assertThat(outcome.getMessage()).contains("'Female' 不是有效值。有效值为: male, female, unknown");
    }

    public enum Gender {
        male, female, unknown
    }

    public static class GenderCondition extends EnumPropertyCondition<Gender> {
        protected GenderCondition(Gender expectedValue) {
            super(expectedValue, Gender.class, PROPERTY_NAME);
        }
    }
}
