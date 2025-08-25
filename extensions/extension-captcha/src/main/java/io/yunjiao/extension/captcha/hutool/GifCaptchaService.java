package io.yunjiao.extension.captcha.hutool;

import cn.hutool.captcha.GifCaptcha;
import cn.hutool.core.util.IdUtil;
import io.yunjiao.extension.common.captcha.CaptchaCategory;
import io.yunjiao.extension.common.captcha.CaptchaData;
import lombok.RequiredArgsConstructor;

/**
 * gif验证码 服务
 *
 * @author yangyunjiao
 */
@RequiredArgsConstructor
public class GifCaptchaService extends AbstractCaptchaService {
    /**
     * 创建器
     */
    private final GifCaptchaBuilder builder;

    @Override
    public CaptchaData draw() {
        GifCaptcha captcha = builder.build();
        String code = captcha.getCode();
        return CaptchaData.builder().key(IdUtil.fastSimpleUUID())
                .code(code)
                .category(getCategory())
                .captchaImage(captcha.getImageBytes())
                .build();
    }

    @Override
    public CaptchaCategory getCategory() {
        return CaptchaCategory.hutoolGif;
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
