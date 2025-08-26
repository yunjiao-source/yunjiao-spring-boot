package yunjiao.springboot.extension.common.captcha;

import yunjiao.springboot.extension.common.lang.EnumCache;
import lombok.Getter;
import lombok.ToString;

/**
 * 验证码类别
 *
 * @author yangyunjiao
 */
@Getter
@ToString
public enum CaptchaCategory {
    hutoolLine(CaptchaCategory.HUTOOL_LINE_CAPTCHA, "png", "Hutool线段干扰验证码"),
    hutoolCircle(CaptchaCategory.HUTOOL_CIRCLE_CAPTCHA, "png", "Hutool圆圈干扰验证码"),
    hutoolShear(CaptchaCategory.HUTOOL_SHEAR_CAPTCHA, "png", "Hutool扭曲干扰验证码"),
    hutoolGif(CaptchaCategory.HUTOOL_GIF_CAPTCHA, "gif", "Hutool GIF验证码");

    public static final String HUTOOL_LINE_CAPTCHA = "HUTOOL_LINE";
    public static final String HUTOOL_CIRCLE_CAPTCHA = "HUTOOL_CIRCLE";
    public static final String HUTOOL_SHEAR_CAPTCHA = "HUTOOL_SHEAR";
    public static final String HUTOOL_GIF_CAPTCHA = "HUTOOL_GIF";

    /**
     * 代码
     */
    private final String code;

    /**
     * 描述
     */
    private final String description;

    /**
     * 扩展名
     */
    private final String ext;

    CaptchaCategory(String code, String ext, String description) {
        this.code = code;
        this.ext = ext;
        this.description = description;
    }

    static {
        EnumCache.registerByName(CaptchaCategory.class, CaptchaCategory.values());
        EnumCache.registerByValue(CaptchaCategory.class, CaptchaCategory.values(), CaptchaCategory::getCode);
    }
}
