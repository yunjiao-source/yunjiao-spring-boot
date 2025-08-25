package io.yunjiao.extension.captcha.hutool;

import cn.hutool.captcha.LineCaptcha;
import io.yunjiao.extension.common.captcha.CaptchaCategory;
import io.yunjiao.extension.common.captcha.CaptchaData;
import lombok.RequiredArgsConstructor;

import java.awt.image.BufferedImage;

/**
 * 线段干扰的验证码 服务
 *
 * @author yangyunjiao
 */
@RequiredArgsConstructor
public class LineCaptchaService extends AbstractCaptchaService {
    /**
     * 创建器
     */
    private final LineCaptchaBuilder builder;

    @Override
    public CaptchaData draw() {
        LineCaptcha captcha = builder.build();
        // 生成码
        String code = captcha.getGenerator().generate();
        // 生成图片
        BufferedImage image = (BufferedImage)captcha.createImage(code);

        handleFuzziness(image);
        return createCaptchaData(code, image);
    }

    @Override
    public CaptchaCategory getCategory() {
        return CaptchaCategory.hutoolLine;
    }

    @Override
    protected Boolean getValidIgnoreCase() {
        return builder.getValidIgnoreCase();
    }

    @Override
    protected Integer getFuzziness() {
        return builder.getFuzziness();
    }

}
