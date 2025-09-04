package yunjiao.springboot.extension.captcha.hutool;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.generator.CodeGenerator;
import yunjiao.springboot.extension.common.model.ColorTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * 抽象的验证码创建者
 *
 * @author yangyunjiao
 */
@Getter
@Setter
public abstract class AbstractCaptchaBuilder<C extends AbstractCaptcha> {
    /**
     * 图片的宽度
     */
    private Integer width;

    /**
     * 图片的高度
     */
    private Integer height;

    /**
     * 验证码干扰元素个数（干扰线宽度）
     */
    private Integer interfereCount;

    /**
     * 背景色
     */
    private ColorTypeEnum backgroundColor;

    /**
     * 文字透明度，取值0~1，1表示不透明
     */
    private Float transparency;

    /**
     * 模糊度（0 - 30）
     */
    private Integer fuzziness;

    /**
     * 字体
     */
    private Font font;

    /**
     * 码生成器
     */
    private CodeGenerator generator;

    /**
     * 校验时是否忽略大小写
     */
    private Boolean validIgnoreCase;

    /**
     * 创建验证码工具，子类实现
     *
     * @return 实例
     */
    protected abstract C createCaptcha();

    /**
     * 填充验证码工具
     * @param captcha 必须值
     */
    protected void fill(AbstractCaptcha captcha) {
        captcha.setFont(font);
        captcha.setBackground(backgroundColor.getMapping());

        if (transparency != null) {
            captcha.setTextAlpha(transparency);
        }
    }

    /**
     * 创建验证码工具
     *
     * @return 实例
     */
    public C build() {
        C captcha = createCaptcha();
        fill(captcha);
        return captcha;
    }

}
