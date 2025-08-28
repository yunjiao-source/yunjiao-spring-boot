package yunjiao.springboot.extension.captcha;

import yunjiao.springboot.extension.common.util.CommonRuntimeException;

/**
 * 验证码异常
 *
 * @author yangyunjiao
 */
public class CaptchaException extends CommonRuntimeException {

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }
}
