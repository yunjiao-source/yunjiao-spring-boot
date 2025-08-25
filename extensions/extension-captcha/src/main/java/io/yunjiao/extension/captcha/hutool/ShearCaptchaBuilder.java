package io.yunjiao.extension.captcha.hutool;

import cn.hutool.captcha.ShearCaptcha;

/**
 * 扭曲干扰验证码 创建器
 *
 * @author yangyunjiao
 */
public class ShearCaptchaBuilder extends AbstractCaptchaBuilder<ShearCaptcha> {
    @Override
    protected ShearCaptcha createCaptcha() {
        return new ShearCaptcha(getWidth(), getHeight(), getGenerator(), getInterfereCount());
    }
}
