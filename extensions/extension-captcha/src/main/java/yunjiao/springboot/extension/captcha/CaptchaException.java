package yunjiao.springboot.extension.captcha;

import yunjiao.springboot.extension.common.util.CommonRuntimeException;

/**
 * 验证码异常
 *
 * @author yangyunjiao
 */
public class CaptchaException extends CommonRuntimeException {

    /**
     * 构造器
     *
     * @param message 消息
     */
    public CaptchaException(String message) {
        super(message);
    }

    /**
     * 构造器
     *
     * @param message 消息
     * @param cause 原异常
     */
    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }
}
