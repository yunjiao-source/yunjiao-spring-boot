package yunjiao.springboot.extension.common.captcha;

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
    /**
     * 线段干扰验证码
     */
    line("png", "线段干扰验证码"),

    /**
     * 圆圈干扰验证码
     */
    circle("png", "圆圈干扰验证码"),

    /**
     * 扭曲干扰验证码
     */
    shear("png", "扭曲干扰验证码"),

    /**
     * GIF验证码
     */
    gif("gif", "GIF验证码"),

    /**
     * 滑块拼图验证码
     */
    blockPuzzle("png", "滑块拼图验证码"),

    /**
     * 文字点选验证码
     */
    clickWord("png", "文字点选验证码"),

    /**
     * 旋转拼图验证码
     */
    rotatePuzzle("png", "旋转拼图验证码");

    /**
     * 描述
     */
    private final String description;

    /**
     * 扩展名
     */
    private final String ext;

    CaptchaCategory(String ext, String description) {
        this.ext = ext;
        this.description = description;
    }
}
