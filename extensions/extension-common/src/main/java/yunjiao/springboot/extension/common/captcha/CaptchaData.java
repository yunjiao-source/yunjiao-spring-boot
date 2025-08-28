package yunjiao.springboot.extension.common.captcha;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Base64;

/**
 * 验证码数据
 *
 * @author yangyunjiao
 */
@Getter
@Setter
@ToString
@Accessors(fluent = true, chain = true)
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
    public String captchaImageBase64Url() {
        if (captchaImage == null) {
            return null;
        }

        String base64 = Base64.getEncoder().encodeToString(captchaImage);
        return "data:image/" + category.getExt() + ";base64," + base64;
    }

    /**
     * 转换成背景图片字符串
     *
     * @return 可能空
     */
    public String backgroundImageBase64Url() {
        if (backgroundImage == null) {
            return null;
        }

        String base64 = Base64.getEncoder().encodeToString(backgroundImage);
        return "data:image/" + category.getExt() + ";base64," + base64;
    }
}
