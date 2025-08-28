package yunjiao.springboot.autoconfigure.captcha;

import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import yunjiao.springboot.extension.captcha.anji.BlockPuzzleCaptchaService;
import yunjiao.springboot.extension.captcha.anji.ClickWorkCaptchaService;
import yunjiao.springboot.extension.captcha.anji.RotatePluzzleCaptchaService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link AnjiCaptchaConfiguration} 单元测试用例
 *
 * @author yangyunjiao
 */
public class AnjiCaptchaConfigurationTest {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    public void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(AnjiCaptchaConfiguration.class));
    }

    @Test
    public void givenDefault_thenConfig() {
        applicationContextRunner
                .run(context -> {
                    assertThat(context).hasSingleBean(CaptchaCacheService.class);
                    assertThat(context).hasSingleBean(CaptchaService.class);
                    assertThat(context).hasSingleBean(BlockPuzzleCaptchaService.class);
                    assertThat(context).hasSingleBean(ClickWorkCaptchaService.class);
                    assertThat(context).hasSingleBean(RotatePluzzleCaptchaService.class);
                });
    }
}
