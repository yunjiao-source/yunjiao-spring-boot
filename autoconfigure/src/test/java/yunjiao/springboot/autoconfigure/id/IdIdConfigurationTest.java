package yunjiao.springboot.autoconfigure.id;

import cn.hutool.core.lang.Snowflake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link HutoolIdConfiguration} 单元测试用例
 *
 * @author yangyunjiao
 */
public class IdIdConfigurationTest {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    public void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(HutoolIdConfiguration.class));
    }

    @Test
    public void givenDefault_thenConfig() {
        applicationContextRunner
                .run(context -> {
                    assertThat(context).hasSingleBean(Snowflake.class);
                });
    }
}
