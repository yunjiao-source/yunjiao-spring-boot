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
public class LineCaptchaServiceTest {
    private static final LineCaptchaService lineCaptchaService;
    private static final LineCaptchaBuilder builder;

    static {
        Font font = new Font(null, Font.PLAIN, 36);
        builder = new LineCaptchaBuilder();
        builder.setWidth(250);
        builder.setHeight(50);
        builder.setInterfereCount(60);
        builder.setBackgroundColor(ColorType.white);
        builder.setFuzziness(2);
        builder.setValidIgnoreCase(true);
        builder.setFont(font);
        builder.setGenerator(CodeGeneratorType.lowerChar.apply(6));
        lineCaptchaService = new LineCaptchaService(builder);
    }

    @Test
    void whenDraw_thenOk() {
        CaptchaData data = lineCaptchaService.draw();
        assertThat(data.key()).isNotBlank();
        assertThat(data.backgroundImage()).isNull();
        assertThat(data.captchaImage()).isNotNull();
        assertThat(data.category()).isEqualTo(CaptchaCategory.line);
        assertThat(data.code()).hasSize(6);
    }

    @Test
    void givenIgnoreCaseTrue_whenVerify_thenOK() {
        builder.setValidIgnoreCase(true);
        CaptchaData data = lineCaptchaService.draw();

        String uperCode = data.code().toUpperCase();
        assertThat(lineCaptchaService.verify(data.code(), uperCode)).isTrue();
    }

    @Test
    void givenIgnoreCaseFalse_whenVerify_thenOK() {
        builder.setValidIgnoreCase(false);
        CaptchaData data = lineCaptchaService.draw();

        String uperCode = data.code().toUpperCase();
        assertThat(lineCaptchaService.verify(data.code(), uperCode)).isFalse();
    }
}
