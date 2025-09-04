package yunjiao.springboot.extension.captcha.hutool;

import org.junit.jupiter.api.Test;
import yunjiao.springboot.extension.common.captcha.CaptchaCategory;
import yunjiao.springboot.extension.common.captcha.CaptchaData;
import yunjiao.springboot.extension.common.model.ColorTypeEnum;
import yunjiao.springboot.extension.common.model.FontNameEnum;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link ShearCaptchaService} 单元测试用例
 *
 * @author yangyunjiao
 */
public class ShearCaptchaServiceTest {
    private static final ShearCaptchaService shearCaptchaService;
    private static final ShearCaptchaBuilder builder;

    static {
        Font font = FontNameEnum.SimSun.getFont(Font.PLAIN, 36);
        builder = new ShearCaptchaBuilder();
        builder.setWidth(250);
        builder.setHeight(50);
        builder.setInterfereCount(4);
        builder.setBackgroundColor(ColorTypeEnum.white);
        builder.setFuzziness(2);
        builder.setValidIgnoreCase(true);
        builder.setFont(font);
        builder.setGenerator(CodeGeneratorType.numAndChar.apply(6));
        shearCaptchaService = new ShearCaptchaService(builder);
    }

    @Test
    void whenDraw_thenOk() {
        CaptchaData data = shearCaptchaService.draw();
        assertThat(data.key()).isNotBlank();
        assertThat(data.backgroundImage()).isNull();
        assertThat(data.captchaImage()).isNotNull();
        assertThat(data.category()).isEqualTo(CaptchaCategory.shear);
        assertThat(data.code()).hasSize(6);
    }

    @Test
    void givenIgnoreCaseTrue_whenVerify_thenOK() {
        builder.setValidIgnoreCase(true);
        CaptchaData data = shearCaptchaService.draw();

        String uperCode = data.code().toUpperCase();
        assertThat(shearCaptchaService.verify(data.code(), uperCode)).isTrue();
    }

    @Test
    void givenIgnoreCaseFalse_whenVerify_thenOK() {
        builder.setValidIgnoreCase(false);
        CaptchaData data = shearCaptchaService.draw();

        String uperCode = data.code().toUpperCase();
        assertThat(shearCaptchaService.verify(data.code(), uperCode)).isFalse();
    }
}
