package io.yunjiao.spring.boot.autoconfigure.apijson.condition;

import io.yunjiao.spring.apijson.util.ApijsonConsts;
import io.yunjiao.spring.boot.autoconfigure.test.TestUtils;
import io.yunjiao.spring.boot.autoconfigure.util.PropertyNameConsts;
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
 * {@link NewIdStrategyCondition} 单元测试用例
 *
 * @author yangyunjiao
 */
@ExtendWith(MockitoExtension.class)
public class NewIdStrategyConditionTest {
    @Mock
    private AnnotatedTypeMetadata metadata;

    @Test
    void shouldMatchWhenPropertyMatchesExpectedType_Database() {
        MockEnvironment env = new MockEnvironment();
        env.setProperty(PropertyNameConsts.PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY, "database");

        ConditionContext context = TestUtils.createMockContext(env);
        NewIdStrategyCondition.OnDatabase onDatabase = new NewIdStrategyCondition.OnDatabase();
        ConditionOutcome outcome = onDatabase.getMatchOutcome(context, metadata);

        // 验证结果
        assertThat(outcome.isMatch()).isTrue();
        assertThat(outcome.getMessage()).contains("配置值 'database' 匹配期望值");
    }

    @Test
    void shouldMatchWhenPropertyMatchesExpectedType_Snowflake() {
        MockEnvironment env = new MockEnvironment();
        env.setProperty(PropertyNameConsts.PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY, "snowflake");

        ConditionContext context = TestUtils.createMockContext(env);
        NewIdStrategyCondition.OnSnowflake onSnowflake = new NewIdStrategyCondition.OnSnowflake();
        ConditionOutcome outcome = onSnowflake.getMatchOutcome(context, metadata);

        // 验证结果
        assertThat(outcome.isMatch()).isTrue();
        assertThat(outcome.getMessage()).contains("配置值 'snowflake' 匹配期望值");
    }

    @Test
    void shouldMatchWhenPropertyMatchesExpectedType_Timestamp() {
        MockEnvironment env = new MockEnvironment();
        env.setProperty(PropertyNameConsts.PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY, "timestamp");

        ConditionContext context = TestUtils.createMockContext(env);
        NewIdStrategyCondition.OnTimestamp onTimestamp = new NewIdStrategyCondition.OnTimestamp();
        ConditionOutcome outcome = onTimestamp.getMatchOutcome(context, metadata);

        // 验证结果
        assertThat(outcome.isMatch()).isTrue();
        assertThat(outcome.getMessage()).contains("配置值 'timestamp' 匹配期望值");
    }

    @Test
    void shouldMatchWhenPropertyMatchesExpectedType_Uuid() {
        MockEnvironment env = new MockEnvironment();
        env.setProperty(PropertyNameConsts.PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY, "uuid");

        ConditionContext context = TestUtils.createMockContext(env);
        NewIdStrategyCondition.OnUuid onUuid = new NewIdStrategyCondition.OnUuid();
        ConditionOutcome outcome = onUuid.getMatchOutcome(context, metadata);

        // 验证结果
        assertThat(outcome.isMatch()).isTrue();
        assertThat(outcome.getMessage()).contains("配置值 'uuid' 匹配期望值");
    }
}
