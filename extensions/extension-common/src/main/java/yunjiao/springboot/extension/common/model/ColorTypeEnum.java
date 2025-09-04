package yunjiao.springboot.extension.common.model;

import lombok.Getter;

import java.awt.*;

/**
 * 颜色类型
 *
 * @author yangyunjiao
 */
@Getter
public enum ColorTypeEnum {
    /**
     * 白色
     */
    white(Color.WHITE),

    /**
     * 浅灰色
     */
    lightGray(Color.LIGHT_GRAY),

    /**
     * 灰色
     */
    gray(Color.GRAY),

    /**
     * 深灰色
     */
    darkGray(Color.DARK_GRAY),


    /**
     * 黑色
     */
    black(Color.BLACK),

    /**
     * 红色
     */
    red(Color.RED),

    /**
     * 粉红色
     */
    pink(Color.PINK),

    /**
     * 橘色
     */
    orange(Color.ORANGE),

    /**
     * 黄色
     */
    yellow(Color.YELLOW),

    /**
     * 绿色
     */
    green(Color.GREEN),

    /**
     * 洋红色
     */
    magenta(Color.MAGENTA),

    /**
     * 青色
     */
    cyan(Color.CYAN),

    /**
     * 蓝色
     */
    blue(Color.BLUE);

    private final Color mapping;

    ColorTypeEnum(Color mapping) {
        this.mapping = mapping;
    }
}
