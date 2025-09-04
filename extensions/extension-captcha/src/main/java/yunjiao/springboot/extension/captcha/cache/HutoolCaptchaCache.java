package yunjiao.springboot.extension.captcha.cache;

import cn.hutool.cache.impl.TimedCache;

import java.time.Duration;
import java.util.Optional;

/**
 * 使用Hutool TimedCache实现的验证码缓存器
 *
 * @author yangyunjiao
 */
public class HutoolCaptchaCache implements CaptchaCache {
    private final TimedCache<String, String> cache;

    /**
     * 构造器
     *
     * @param expired 过期时间
     * @param scheduleInterval 定时间隔，清理过期缓存
     */
    public HutoolCaptchaCache(Duration expired, Duration scheduleInterval) {
        this.cache = new TimedCache<>(expired.toMillis());
        this.cache.schedulePrune(scheduleInterval.toMillis());
    }

    @Override
    public void put(String key, String value) {
        cache.put(key, value);
    }

    @Override
    public Optional<String> get(String key) {
        return Optional.ofNullable(cache.get(key));
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public long size() {
        return cache.size();
    }
}
