package io.yunjiao.extension.common.captcha;

import lombok.Data;

/**
 * 验证码校验对象
 *
 * @author yangyunjiao
 */
@Data
public class CaptchaValidate {
    /**
     * 验证码唯一标识，通常是uuid字符
     */
    private String key;

    /**
     * 验证码
     */
    private Object code;


    /**
     * 分类
     */
    private CaptchaCategory category;
}
