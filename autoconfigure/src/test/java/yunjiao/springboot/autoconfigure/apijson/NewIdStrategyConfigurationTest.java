package yunjiao.springboot.autoconfigure.apijson;

import cn.hutool.core.lang.Snowflake;
import yunjiao.springboot.autoconfigure.util.PropertyNameConsts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import yunjiao.springboot.extension.apjson.orm.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link NewIdStrategyConfiguration}单元测试用例
 *
 * @author yangyunjiao
 */
public class NewIdStrategyConfigurationTest {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(NewIdStrategyConfiguration.class));
    }

    @Test
    void givenPropertySnowflake_whenConfig() {
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
    void givenPropertyTimestamp_whenConfig() {
        applicationContextRunner
                .withPropertyValues(PropertyNameConsts.PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY + "=" + ApijsonProperties.NewIdStrategy.timestamp)
                .run(context -> {
                    assertThat(context).hasSingleBean(NewIdStrategy.class);
                    NewIdStrategy strategy = context.getBean(NewIdStrategy.class);
                    assertInstanceOf(NewIdTimestampStrategy.class, strategy);
                });
    }

    @Test
    void givenPropertyUuid_whenConfig() {
        applicationContextRunner
                .withPropertyValues(PropertyNameConsts.PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY + "=" + ApijsonProperties.NewIdStrategy.uuid)
                .run(context -> {
                    assertThat(context).hasSingleBean(NewIdStrategy.class);
                    NewIdStrategy strategy = context.getBean(NewIdStrategy.class);
                    assertInstanceOf(NewIdUuidStrategy.class, strategy);
                });
    }

    @Test
    void givenPropertyCustom_whenConfig() {
        applicationContextRunner
                .withPropertyValues(PropertyNameConsts.PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY + "=" + ApijsonProperties.NewIdStrategy.custom)
                .run(context -> {
                    assertThat(context).hasSingleBean(NewIdStrategy.class);
                    NewIdStrategy strategy = context.getBean(NewIdStrategy.class);
                    assertInstanceOf(NewIdExceptionStrategy.class, strategy);
                });
    }

}
