package io.yunjiao.extension.captcha.hutool;

import cn.hutool.captcha.CircleCaptcha;

/**
 * 圆圈干扰验证码 创建器
 *
 * @author yangyunjiao
 */
public class CircleCaptchaBuilder extends AbstractCaptchaBuilder<CircleCaptcha> {

    @Override
    protected CircleCaptcha createCaptcha() {
        return new CircleCaptcha(getWidth(), getHeight(), getGenerator(), getInterfereCount());
    }
}
