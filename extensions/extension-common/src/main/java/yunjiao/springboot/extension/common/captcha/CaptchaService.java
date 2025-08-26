package yunjiao.springboot.extension.common.captcha;

/**
 * 验证码服务接口
 *
 * @author yangyunjiao
 */
public interface CaptchaService {
    /**
     * 验证码绘制
     *
     * @return 验证码信息
     */
    CaptchaData draw();

    /**
     * 校验验证码
     *
     * @param orignalCode 原始验证码
     * @param userCode 用户输入验证码
     * @return 相同返回true，否则false
     */
    boolean verify(Object orignalCode, Object userCode);

    /**
     * 获取分类
     *
     * @return 验证码类别
     */
    CaptchaCategory getCategory();
}
