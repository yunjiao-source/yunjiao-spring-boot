package yunjiao.springboot.autoconfigure.captcha;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import yunjiao.springboot.autoconfigure.util.PropertyNameConsts;
import yunjiao.springboot.extension.common.model.FontStyle;

/**
 * anji验证码属性
 *
 * @author yangyunjiao
 */
@Data
@ConfigurationProperties(prefix = PropertyNameConsts.PROPERTY_PREFIX_CAPTCHA_ANJI)
public class AnjiCaptchaProperties {
    /**
     * 右下角水印文字(我的水印).
     */
    private String waterMark = "我的水印";

    /**
     * 右下角水印字体(文泉驿正黑).
     */
    private String waterFont = "WenQuanZhengHei.ttf";

    /**
     * 滑动验证码配置
     */
    @NestedConfigurationProperty
    private BlockPuzzleOptions blockPuzzle = new BlockPuzzleOptions();

    /**
     * 点选文字验证码配置
     */
    @NestedConfigurationProperty
    private ClickWordOptions clickWord = new ClickWordOptions();

    /**
     * 滑动验证码 属性
     */
    @Data
    public static class BlockPuzzleOptions {
        /**
         * 滑动拼图底图路径.
         */
        private String jigsaw = "";

        /**
         * 校验滑动拼图允许误差偏移量(默认5像素).
         */
        private Integer slipOffset = 5;

        /**
         * 滑块干扰项(0/1/2)
         */
        private Integer interferenceOptions = 0;
    }

    /**
     * 点选文字验证码 属性
     */
    @Data
    public static class ClickWordOptions {
        /**
         * 点选文字底图路径.
         */
        private String picClick = "";

        /**
         * 点选文字验证码的文字字体(文泉驿正黑).
         */
        private String fontType = "WenQuanZhengHei.ttf";

        /**
         * 点选字体样式
         */
        private FontStyle fontStyle = FontStyle.bold;

        /**
         * 点选字体大小
         */
        private Integer fontSize = 25;

        /**
         * 点选文字个数
         */
        private Integer clickWordCount = 4;

        /**
         * 校验拼图允许误差(默认13像素).
         */
        private Integer slipOffset = 13;
    }
}
