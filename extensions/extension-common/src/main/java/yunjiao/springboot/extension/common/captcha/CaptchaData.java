package yunjiao.springboot.extension.common.captcha;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Base64;

/**
 * 验证码数据
 *
 * @author yangyunjiao
 */
@Getter
@Builder
@ToString
public class CaptchaData implements Serializable {
    /**
     * 验证码唯一标识，通常是uuid字符
     */
    private String key;

    /**
     * 验证码图片
     */
    private byte[] captchaImage;

    /**
     * 背景图片
     */
    private byte[] backgroundImage;

    /**
     * 验证码
     */
    private String code;

    /**
     * 分类
     */
    private CaptchaCategory category;

    /**
     * 转换成图片字符串
     *
     * @return 可能空
     */
    public String getCaptchaImageBase64() {
        if (captchaImage == null) {
            return null;
        }

        String base64 = Base64.getEncoder().encodeToString(captchaImage);
        return "data:" + category.getExt() + ";base64," + base64;
    }

    /**
     * 转换成背景图片字符串
     *
     * @return 可能空
     */
    public String getBackgroundImageBase64() {
        if (backgroundImage == null) {
            return null;
        }

        String base64 = Base64.getEncoder().encodeToString(backgroundImage);
        return "data:" + category.getExt() + ";base64," + base64;
    }
}
