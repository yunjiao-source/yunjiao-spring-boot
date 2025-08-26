package yunjiao.springboot.autoconfigure.captcha;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link CaptchaAutoConfiguration} 单元测试用例
 *
 * @author yangyunjiao
 */
public class CaptchaAutoConfigurationTest {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    public void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(CaptchaAutoConfiguration.class));
    }

    @Test
    public void givenDefault_thenConfig() {
        applicationContextRunner
                .run(context -> {
                    assertThat(context).hasSingleBean(CaptchaServiceFactory.class);

                    CaptchaServiceFactory factory = context.getBean(CaptchaServiceFactory.class);
                    assertThat(factory.getServices()).hasSize(4);
                });
    }
}
