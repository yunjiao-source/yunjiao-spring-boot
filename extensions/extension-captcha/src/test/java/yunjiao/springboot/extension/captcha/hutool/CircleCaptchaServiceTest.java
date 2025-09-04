package yunjiao.springboot.extension.captcha.hutool;

import org.junit.jupiter.api.Test;
import yunjiao.springboot.extension.common.captcha.CaptchaCategory;
import yunjiao.springboot.extension.common.captcha.CaptchaData;
import yunjiao.springboot.extension.common.model.ColorTypeEnum;
import yunjiao.springboot.extension.common.model.FontNameEnum;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link CircleCaptchaService} 单元测试用例
 *
 * @author yangyunjiao
 */
public class CircleCaptchaServiceTest {
    private static final CircleCaptchaService circleCaptchaService;
    private static final CircleCaptchaBuilder builder;

    static {
        Font font = FontNameEnum.Default.getFont(Font.PLAIN, 36);
        builder = new CircleCaptchaBuilder();
        builder.setWidth(250);
        builder.setHeight(50);
        builder.setInterfereCount(30);
        builder.setBackgroundColor(ColorTypeEnum.white);
        builder.setFuzziness(2);
        builder.setValidIgnoreCase(true);
        builder.setFont(font);
        builder.setGenerator(CodeGeneratorType.lowerChar.apply(6));
        circleCaptchaService = new CircleCaptchaService(builder);
    }

    @Test
    void whenDraw_thenOk() {
        CaptchaData data = circleCaptchaService.draw();
        assertThat(data.key()).isNotBlank();
        assertThat(data.backgroundImage()).isNull();
        assertThat(data.captchaImage()).isNotNull();
        assertThat(data.category()).isEqualTo(CaptchaCategory.circle);
        assertThat(data.code()).hasSize(6);
    }

    @Test
    void givenIgnoreCaseTrue_whenVerify_thenOK() {
        builder.setValidIgnoreCase(true);
        CaptchaData data = circleCaptchaService.draw();

        String uperCode = data.code().toUpperCase();
        assertThat(circleCaptchaService.verify(data.code(), uperCode)).isTrue();
    }

    @Test
    void givenIgnoreCaseFalse_whenVerify_thenOK() {
        builder.setValidIgnoreCase(false);
        CaptchaData data = circleCaptchaService.draw();

        String uperCode = data.code().toUpperCase();
        assertThat(circleCaptchaService.verify(data.code(), uperCode)).isFalse();
    }
}
