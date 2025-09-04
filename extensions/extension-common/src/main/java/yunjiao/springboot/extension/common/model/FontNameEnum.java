package yunjiao.springboot.extension.common.model;

import lombok.Getter;
import yunjiao.springboot.extension.common.util.FontCache;

import java.awt.*;

/**
 * 字体名称
 *
 * @author yangyunjiao
 */
@Getter
public enum FontNameEnum {
    // 阿里巴巴普惠体 3.0 (下载地址：https://www.alibabafonts.com/#/font)
    AlibabaPuHuiTi355Regular("阿里巴巴普惠体 3.0 55 Regular"),
    AlibabaPuHuiTi335Thin("阿里巴巴普惠体 3.0 35 Thin"),
    AlibabaPuHuiTi345Light("阿里巴巴普惠体 3.0 45 Light"),
    AlibabaPuHuiTi355RegularL3("阿里巴巴普惠体 3.0 55 Regular L3"),
    AlibabaPuHuiTi365Medium("阿里巴巴普惠体 3.0 65 Medium"),
    AlibabaPuHuiTi375SemiBold("阿里巴巴普惠体 3.0 75 SemiBold"),
    AlibabaPuHuiTi385Bold("阿里巴巴普惠体 3.0 85 Bold"),
    AlibabaPuHuiTi395ExtraBold("阿里巴巴普惠体 3.0 95 ExtraBold"),
    AlibabaPuHuiTi3105Heavy("阿里巴巴普惠体 3.0 105 Heavy"),
    AlibabaPuHuiTi3115Black("阿里巴巴普惠体 3.0 115 Black"),

    // 思源黑体 (下载地址：https://github.com/adobe-fonts/source-han-sans/releases)
    SourceHanSansVF("Source Han Sans VF"),

    // 思源宋体 (下载地址：https://github.com/adobe-fonts/source-han-serif/releases)
    SourceHanSerifVF("Source Han Serif VF"),


    // 通用字体
    SimSun("宋体"),
    NSimSun("新宋体"),
    Default(null),
    ;


    private final String name;

    FontNameEnum(String name) {
        this.name = name;
    }

    /**
     * 获取字体
     * @param style 字体样式 (Font.PLAIN, Font.BOLD, Font.ITALIC)
     * @param size 字体大小
     * @return 请求的字体
     */
    public Font getFont(int style, float size) {
        return FontCache.getInstance().getFont(getName(), style, size);
    }

    /**
     * 获取字体 (默认样式和大小)
     *
     * @return 请求的字体
     */
    public Font getFont() {
        return FontCache.getInstance().getFont(getName());
    }
}
