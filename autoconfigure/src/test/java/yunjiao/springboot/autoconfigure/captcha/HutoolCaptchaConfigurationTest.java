package yunjiao.springboot.autoconfigure.captcha;

import yunjiao.springboot.extension.captcha.hutool.CircleCaptchaService;
import yunjiao.springboot.extension.captcha.hutool.GifCaptchaService;
import yunjiao.springboot.extension.captcha.hutool.LineCaptchaService;
import yunjiao.springboot.extension.captcha.hutool.ShearCaptchaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link HutoolCaptchaConfiguration} 单元测试用例
 *
 * @author yangyunjiao
 */
public class HutoolCaptchaConfigurationTest {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(HutoolCaptchaConfiguration.class));
    }

    @Test
    void givenDefault_thenConfig() {
        applicationContextRunner
                .run(context -> {
                    assertThat(context).hasSingleBean(LineCaptchaService.class);
                    assertThat(context).hasSingleBean(CircleCaptchaService.class);
                    assertThat(context).hasSingleBean(ShearCaptchaService.class);
                    assertThat(context).hasSingleBean(GifCaptchaService.class);
                });
    }
}
