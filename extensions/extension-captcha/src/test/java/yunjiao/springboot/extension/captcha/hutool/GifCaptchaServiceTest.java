package yunjiao.springboot.extension.captcha.hutool;

import org.junit.jupiter.api.Test;
import yunjiao.springboot.extension.common.captcha.CaptchaCategory;
import yunjiao.springboot.extension.common.captcha.CaptchaData;
import yunjiao.springboot.extension.common.model.ColorType;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link LineCaptchaService} 单元测试用例
 *
 * @author yangyunjiao
 */
public class GifCaptchaServiceTest {
    private static final GifCaptchaService gifCaptchaService;
    private static final GifCaptchaBuilder builder;

    static {
        Font font = new Font(null, Font.PLAIN, 36);
        builder = new GifCaptchaBuilder();
        builder.setWidth(250);
        builder.setHeight(50);
        builder.setInterfereCount(10);
        builder.setBackgroundColor(ColorType.white);
        builder.setFuzziness(2);
        builder.setValidIgnoreCase(true);
        builder.setFont(font);
        builder.setGenerator(CodeGeneratorType.lowerChar.apply(6));
        builder.setQuality(10);
        builder.setRepeat(0);
        builder.setMinColor(0);
        builder.setMaxColor(255);

        gifCaptchaService = new GifCaptchaService(builder);
    }

    @Test
    void whenDraw_thenOk() {
        CaptchaData data = gifCaptchaService.draw();
        assertThat(data.key()).isNotBlank();
        assertThat(data.backgroundImage()).isNull();
        assertThat(data.captchaImage()).isNotNull();
        assertThat(data.category()).isEqualTo(CaptchaCategory.gif);
        assertThat(data.code()).hasSize(6);
    }

    @Test
    void givenIgnoreCaseTrue_whenVerify_thenOK() {
        builder.setValidIgnoreCase(true);
        CaptchaData data = gifCaptchaService.draw();

        String uperCode = data.code().toUpperCase();
        assertThat(gifCaptchaService.verify(data.code(), uperCode)).isTrue();
    }

    @Test
    void givenIgnoreCaseFalse_whenVerify_thenOK() {
        builder.setValidIgnoreCase(false);
        CaptchaData data = gifCaptchaService.draw();

        String uperCode = data.code().toUpperCase();
        assertThat(gifCaptchaService.verify(data.code(), uperCode)).isFalse();
    }
}
