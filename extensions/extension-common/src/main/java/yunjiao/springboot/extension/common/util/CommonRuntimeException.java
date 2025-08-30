package yunjiao.springboot.extension.common.util;

/**
 * 通用异常
 *
 * @author yangyunjiao
 */
public class CommonRuntimeException extends RuntimeException {
    /**
     * 构造器
     */
    public CommonRuntimeException() {
    }

    /**
     * 构造器
     *
     * @param message 消息
     */
    public CommonRuntimeException(String message) {
        super(message);
    }

    /**
     * 构造器
     *
     * @param message 消息
     * @param cause 源异常
     */
    public CommonRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造器
     *
     * @param cause 源异常
     */
    public CommonRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造器
     *
     * @param message 消息
     * @param cause 源异常
     * @param enableSuppression whether or not suppression is enabled or disabled
     * @param writableStackTrace 堆栈跟踪是否应该是可写的
     */
    public CommonRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
