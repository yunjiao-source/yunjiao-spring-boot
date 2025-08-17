package io.yunjiao.spring.boot.autoconfigure.apijson;

import cn.hutool.core.lang.Snowflake;
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
    public void testSnowflake() {
        applicationContextRunner.run(context -> {
            assertThat(context).hasSingleBean(Snowflake.class);
        });
    }
}
