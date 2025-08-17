package io.yunjiao.spring.boot.hutool;

import cn.hutool.core.lang.Snowflake;
import io.yunjiao.spring.boot.autoconfigure.hutool.SnowflakeAutoConfiguration;
import io.yunjiao.spring.boot.autoconfigure.util.PropertyNameConsts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link SnowflakeAutoConfiguration} 单元测试用例
 *
 * @author yangyunjiao
 */
public class SnowflakeAutoConfigurationTest {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    public void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(SnowflakeAutoConfiguration.class));
    }

    @Test
    public void testDisable() {
        applicationContextRunner
                .withPropertyValues(PropertyNameConsts.PROPERTY_PREFIX_HUTOOL + ".snowflake=false")
                .run(context -> {
                    assertThat(context).doesNotHaveBean(Snowflake.class);
                });
    }

    @Test
    public void testEnable() {
        applicationContextRunner
                .run(context -> {
                    assertThat(context).hasSingleBean(Snowflake.class);
                });
    }
}
