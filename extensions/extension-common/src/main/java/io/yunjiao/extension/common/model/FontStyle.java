package io.yunjiao.extension.common.model;

import lombok.Getter;

import java.awt.*;

/**
 * 字体风格
 *
 * @author yangyunjiao
 */
@Getter
public enum FontStyle {
    /**
     * 正常体
     */
    plain(Font.PLAIN),

    /**
     * 粗体
     */
    bold(Font.BOLD),

    /**
     * 斜体
     */
    italic(Font.ITALIC);

    private final int mapping;

    FontStyle(int mapping) {
        this.mapping = mapping;
    }
}
