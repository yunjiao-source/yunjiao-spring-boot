package yunjiao.springboot.extension.common.model;

import lombok.Getter;

import java.awt.*;

/**
 * 透明度类型
 *
 * @author yangyunjiao
 */
@Getter
public enum TransparencyType {
    /**
     * {@link AlphaComposite#Clear}
     */
    clear(AlphaComposite.Clear),

    /**
     * {@link AlphaComposite#Src}
     */
    src(AlphaComposite.Src),

    /**
     * {@link AlphaComposite#Dst}
     */
    dst(AlphaComposite.Dst),

    /**
     * {@link AlphaComposite#SrcOver}
     */
    srcOver(AlphaComposite.SrcOver),

    /**
     * {@link AlphaComposite#DstOver}
     */
    dstOver(AlphaComposite.DstOver),

    /**
     * {@link AlphaComposite#SrcIn}
     */
    srcIn(AlphaComposite.SrcIn),

    /**
     * {@link AlphaComposite#DstIn}
     */
    dstIn(AlphaComposite.DstIn),

    /**
     * {@link AlphaComposite#SrcOut}
     */
    srcOut(AlphaComposite.SrcOut),

    /**
     * {@link AlphaComposite#DstOut}
     */
    dstOut(AlphaComposite.DstOut),

    /**
     * {@link AlphaComposite#SrcAtop}
     */
    srcAtop(AlphaComposite.SrcAtop),

    /**
     * {@link AlphaComposite#DstAtop}
     */
    dstAtop(AlphaComposite.DstAtop),

    /**
     * {@link AlphaComposite#Xor}
     */
    xOr(AlphaComposite.Xor);

    private final AlphaComposite mapping;

    TransparencyType(AlphaComposite mapping) {
        this.mapping = mapping;
    }
}
