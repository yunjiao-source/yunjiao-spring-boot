package io.yunjiao.extension.common.captcha;

import lombok.Data;

/**
 * 验证码响应
 *
 * @author yangyunjiao
 */
@Data
public class CaptchaReponse {
    /**
     * 验证码唯一标识，通常是uuid字符
     */
    private String key;

    /**
     * 验证码图片
     */
    private String captchaImageBase64;

    /**
     * 背景图片
     */
    private String backgroundImageBase64;


    /**
     * 分类
     */
    private CaptchaCategory category;

    public static CaptchaReponse of(CaptchaData data) {
        CaptchaReponse reponse = new CaptchaReponse();
        reponse.setCategory(data.getCategory());
        reponse.setKey(data.getKey());
        reponse.setBackgroundImageBase64(data.getBackgroundImageBase64());
        reponse.setCaptchaImageBase64(data.getCaptchaImageBase64());
        return reponse;
    }
}
