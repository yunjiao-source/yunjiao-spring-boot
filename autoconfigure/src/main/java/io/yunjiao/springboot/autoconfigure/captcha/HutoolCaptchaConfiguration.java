package io.yunjiao.springboot.autoconfigure.captcha;

import cn.hutool.captcha.ICaptcha;
import cn.hutool.core.lang.Assert;
import io.yunjiao.extension.captcha.hutool.*;
import io.yunjiao.extension.common.captcha.CaptchaCategory;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

/**
 * Hutool验证码配置
 *
 * @author yangyunjiao
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ICaptcha.class})
@EnableConfigurationProperties({HutoolCaptchaProperties.class})
public class HutoolCaptchaConfiguration {
    /**
     * {@link PostConstruct} 注解方法
     */
    @PostConstruct
    public void postConstruct() {
        log.info("Hutool Captcha Configuration");
    }

    @Bean(CaptchaCategory.HUTOOL_LINE_CAPTCHA)
    LineCaptchaService lineCaptchaService(HutoolCaptchaProperties properties) {
        HutoolCaptchaProperties.DrawingOptions options = properties.getLine();
        validate(options);
        Font font = createFont(options.getFont());

        LineCaptchaBuilder lcb = new LineCaptchaBuilder();
        fillBuilder(lcb, options);

        LineCaptchaService service = new LineCaptchaService(lcb);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Line Captcha Service:{}]", service);
        }
        return service;
    }

    @Bean(CaptchaCategory.HUTOOL_CIRCLE_CAPTCHA)
    CircleCaptchaService circleCaptchaService(HutoolCaptchaProperties properties) {
        HutoolCaptchaProperties.DrawingOptions options = properties.getCircle();
        validate(options);

        CircleCaptchaBuilder ccb = new CircleCaptchaBuilder();
        fillBuilder(ccb, options);

        CircleCaptchaService service = new CircleCaptchaService(ccb);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Circle Captcha Service:{}]", service);
        }
        return service;
    }

    @Bean(CaptchaCategory.HUTOOL_SHEAR_CAPTCHA)
    ShearCaptchaService sheareCaptchaService(HutoolCaptchaProperties properties) {
        HutoolCaptchaProperties.DrawingOptions options = properties.getShear();
        validate(options);

        ShearCaptchaBuilder scb = new ShearCaptchaBuilder();
        fillBuilder(scb, options);

        ShearCaptchaService service = new ShearCaptchaService(scb);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Shear Captcha Service:{}]", service);
        }
        return service;
    }


    @Bean(CaptchaCategory.HUTOOL_GIF_CAPTCHA)
    GifCaptchaService gifCaptchaService(HutoolCaptchaProperties properties) {
        HutoolCaptchaProperties.GifDrawingOptions options = properties.getGif();
        validate(options);
        Assert.isTrue(options.getQuality() >= 1 && options.getQuality() <= 20, "验证码配置属性‘quality‘值必须在[1, 20]之间");
        Assert.isTrue(options.getRepeat() >= 0, "验证码配置属性‘repeat‘值必须大于0");
        Assert.isTrue(options.getMinColor() >= 0 && options.getMinColor() <= 255, "验证码配置属性‘minColor‘值必须在[0, 255]之间");
        Assert.isTrue(options.getMaxColor() >= 0 && options.getMaxColor() <= 255, "验证码配置属性‘maxColor‘值必须在[0, 255]之间");

        GifCaptchaBuilder gcb = new GifCaptchaBuilder();
        fillBuilder(gcb, options);
        gcb.setQuality(options.getQuality());
        gcb.setRepeat(options.getRepeat());
        gcb.setMinColor(options.getMinColor());
        gcb.setMaxColor(options.getMaxColor());

        GifCaptchaService service = new GifCaptchaService(gcb);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Gif Captcha Service:{}]", service);
        }
        return service;
    }

    private void validate(HutoolCaptchaProperties.DrawingOptions drawing) {
        Assert.isTrue(drawing.getWidth() > 0, "验证码配置属性‘width‘值必须大于0");
        Assert.isTrue(drawing.getHeight() > 0, "验证码配置属性‘height‘值必须大于0");
        Assert.isTrue(drawing.getInterfereCount() > 0, "验证码配置属性‘interfereCount‘值必须大于0");

        Float transparency = drawing.getTransparency();
        if (transparency != null) {
            Assert.isTrue(drawing.getTransparency() >= 0 && drawing.getTransparency() <= 1, "验证码配置属性‘transparency‘值必须在[0, 1]之间");
        }
        Assert.isTrue(drawing.getFuzziness() >= 0 && drawing.getFuzziness() <= 30, "验证码配置属性‘fuzziness‘值必须在[0, 30]之间");


        HutoolCaptchaProperties.CodeOptions code = drawing.getCode();
        Assert.isTrue(code.getLength() > 0, "验证码配置属性‘code.length‘值必须大于0");

        HutoolCaptchaProperties.FontOptions font = drawing.getFont();
        Assert.isTrue(font.getSize() > 0, "验证码配置属性‘font.size‘值必须大于0");
    }

    private void fillBuilder(AbstractCaptchaBuilder<?> builder, HutoolCaptchaProperties.DrawingOptions options) {
        Font font = createFont(options.getFont());

        builder.setWidth(options.getWidth());
        builder.setHeight(options.getHeight());
        builder.setInterfereCount(options.getInterfereCount());
        builder.setBackgroundColor(options.getBackgroundColor());
        builder.setFuzziness(options.getFuzziness());
        builder.setValidIgnoreCase(options.getValidIgnoreCase());
        builder.setFont(font);

        HutoolCaptchaProperties.CodeOptions code = options.getCode();
        builder.setGenerator(code.getGenerator().apply(code.getLength()));
    }

    @SuppressWarnings({"all"})
    private Font createFont(HutoolCaptchaProperties.FontOptions options) {
        return new Font(options.getName(), options.getStyle().getMapping(), options.getSize());
    }
}
