package io.yunjiao.extension.captcha.hutool;

import cn.hutool.captcha.CircleCaptcha;
import io.yunjiao.extension.common.captcha.CaptchaCategory;
import io.yunjiao.extension.common.captcha.CaptchaData;
import lombok.RequiredArgsConstructor;

import java.awt.image.BufferedImage;

/**
 * 圆圈干扰验证码 服务
 *
 * @author yangyunjiao
 */
@RequiredArgsConstructor
public class CircleCaptchaService extends AbstractCaptchaService {
    /**
     * 创建器
     */
    private final CircleCaptchaBuilder builder;

    @Override
    public CaptchaData draw() {
        CircleCaptcha captcha = builder.build();
        // 生成码
        String code = captcha.getGenerator().generate();
        // 生成图片
        BufferedImage image = (BufferedImage)captcha.createImage(code);

        handleFuzziness(image);
        return createCaptchaData(code, image);
    }

    @Override
    public CaptchaCategory getCategory() {
        return CaptchaCategory.hutoolCircle;
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
