package yunjiao.springboot.extension.captcha.cache;

import java.time.Duration;

/**
 * 验证码缓存器工厂(单例)
 *
 * @author yangyunjiao
 */
public class CaptchaCacheFactory {
    private static volatile CaptchaCache instance;

    private CaptchaCacheFactory() {

    }

    public static CaptchaCache initHtoolCache(Duration expireTime) {
        return initHtoolCache(expireTime, Duration.ofSeconds(1L));
    }

    /**
     *
     * @param expireTime 过期时间
     * @param delay
     * @return
     */
    public static CaptchaCache initHtoolCache(Duration expireTime, Duration delay) {
        if (instance == null) {
            synchronized (CaptchaCacheFactory.class) {
                if (instance == null) {
                    instance = new HutoolCaptchaCache(expireTime, delay);
                }
            }
        }
        return instance;
    }

    /**
     *
     * @param expireTime 过期时间
     * @param maxSize 最大缓存大小，0表示无限制
     * @return 验证码缓存器实例
     */
    public static CaptchaCache initGuavaCache(Duration expireTime, long maxSize) {
        if (instance == null) {
            synchronized (CaptchaCacheFactory.class) {
                if (instance == null) {
                    instance = new GuavaCaptchaCache(expireTime, maxSize);
                }
            }
        }
        return instance;
    }

    /**
     * 获取已创建的验证码缓存器实例
     * @return 验证码缓存器实例
     * @throws IllegalStateException 如果实例尚未创建
     */
    public static CaptchaCache getInstance() {
        if (instance == null) {
            throw new IllegalStateException("验证码缓存器尚未初始化，请先调用getInstance(String, long, TimeUnit, long)方法");
        }
        return instance;
    }
}
