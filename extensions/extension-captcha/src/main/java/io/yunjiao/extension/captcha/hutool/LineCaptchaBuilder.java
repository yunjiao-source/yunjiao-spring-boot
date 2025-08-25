package io.yunjiao.extension.captcha.hutool;

import cn.hutool.captcha.LineCaptcha;

/**
 * 线段干扰的验证码 创建器
 *
 * @author yangyunjiao
 */
public class LineCaptchaBuilder extends AbstractCaptchaBuilder<LineCaptcha> {
    @Override
    protected LineCaptcha createCaptcha() {
        return new LineCaptcha(getWidth(), getHeight(), getGenerator(), getInterfereCount());
    }
}
