package io.yunjiao.springboot.autoconfigure.apijson;

import cn.hutool.core.lang.Snowflake;
import io.yunjiao.extension.apjson.orm.*;
import io.yunjiao.springboot.autoconfigure.util.PropertyNameConsts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link NewIdStrategyAutoConfiguration}单元测试用例
 *
 * @author yangyunjiao
 */
public class NewIdStrategyAutoConfigurationTest {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    public void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(NewIdStrategyAutoConfiguration.class));
    }

    @Test
    public void testNewIdSnowflakeStrategy() {
        applicationContextRunner
                .withPropertyValues(PropertyNameConsts.PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY + "=" + ApijsonProperties.NewIdStrategy.snowflake)
                .withBean(Snowflake.class, () -> Mockito.mock(Snowflake.class))
                .run(context -> {
                    assertThat(context).hasSingleBean(NewIdStrategy.class);
                    NewIdStrategy strategy = context.getBean(NewIdStrategy.class);
                    assertInstanceOf(NewIdSnowflakeStrategy.class, strategy);
                });
    }

    @Test
    public void testNewIdTimestampStrategy() {
        applicationContextRunner
                .withPropertyValues(PropertyNameConsts.PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY + "=" + ApijsonProperties.NewIdStrategy.timestamp)
                .run(context -> {
                    assertThat(context).hasSingleBean(NewIdStrategy.class);
                    NewIdStrategy strategy = context.getBean(NewIdStrategy.class);
                    assertInstanceOf(NewIdTimestampStrategy.class, strategy);
                });
    }

    @Test
    public void testNewIdUuidStrategy() {
        applicationContextRunner
                .withPropertyValues(PropertyNameConsts.PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY + "=" + ApijsonProperties.NewIdStrategy.uuid)
                .run(context -> {
                    assertThat(context).hasSingleBean(NewIdStrategy.class);
                    NewIdStrategy strategy = context.getBean(NewIdStrategy.class);
                    assertInstanceOf(NewIdUuidStrategy.class, strategy);
                });
    }

    @Test
    public void testNewIdCustomStrategy() {
        applicationContextRunner
                .withPropertyValues(PropertyNameConsts.PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY + "=" + ApijsonProperties.NewIdStrategy.custom)
                .run(context -> {
                    assertThat(context).hasSingleBean(NewIdStrategy.class);
                    NewIdStrategy strategy = context.getBean(NewIdStrategy.class);
                    assertTrue(strategy instanceof NewIdExceptionStrategy);
                });
    }

}
