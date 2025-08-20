package io.yunjiao.springboot.autoconfigure.apijson.condition;

import io.yunjiao.springboot.autoconfigure.test.TestUtils;
import io.yunjiao.springboot.autoconfigure.util.PropertyNameConsts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link ApllicationCondition} 单元测试用例
 *
 * @author yangyunjiao
 */
@ExtendWith(MockitoExtension.class)
public class ApllicationConditionTest {
    @Mock
    private AnnotatedTypeMetadata metadata;

    @Test
    void shouldMatchWhenPropertyMatchesExpectedType_Gson() {
        MockEnvironment env = new MockEnvironment();
        env.setProperty(PropertyNameConsts.PROPERTY_PREFIX_APIJSON_APPLICATION, "gson");

        ConditionContext context = TestUtils.createMockContext(env);
        ApllicationCondition.OnGson onGson = new ApllicationCondition.OnGson();
        ConditionOutcome outcome = onGson.getMatchOutcome(context, metadata);

        // 验证结果
        assertThat(outcome.isMatch()).isTrue();
        assertThat(outcome.getMessage()).contains("配置值 'gson' 匹配期望值");
    }

    @Test
    void shouldMatchWhenPropertyMatchesExpectedType_Fastjson2() {
        MockEnvironment env = new MockEnvironment();
        env.setProperty(PropertyNameConsts.PROPERTY_PREFIX_APIJSON_APPLICATION, "fastjson2");

        ConditionContext context = TestUtils.createMockContext(env);
        ApllicationCondition.OnFastjson2 onFastjson2 = new ApllicationCondition.OnFastjson2();
        ConditionOutcome outcome = onFastjson2.getMatchOutcome(context, metadata);

        // 验证结果
        assertThat(outcome.isMatch()).isTrue();
        assertThat(outcome.getMessage()).contains("配置值 'fastjson2' 匹配期望值");
    }
}
