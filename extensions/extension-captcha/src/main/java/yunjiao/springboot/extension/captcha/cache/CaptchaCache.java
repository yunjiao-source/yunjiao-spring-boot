package yunjiao.springboot.extension.captcha.cache;

import java.util.Optional;

/**
 * 验证码缓存
 *
 * @author yangyunjiao
 */
public interface CaptchaCache {
    /**
     * 存储验证码
     * @param key 键
     * @param value 值(验证码)
     */
    void put(String key, String value);

    /**
     * 获取验证码
     * @param key 键
     * @return 验证码值，如果不存在或已过期则返回null
     */
    Optional<String> get(String key);

    /**
     * 移除验证码
     * @param key 键
     */
    void remove(String key);

    /**
     * 清空所有缓存
     */
    void clear();

    /**
     * 获取缓存大小
     * @return 当前缓存中的元素数量
     */
    long size();
}
