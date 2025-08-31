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

    public HutoolCaptchaCache(Duration expireMillis, Duration delay) {
        this.cache = new TimedCache<>(expireMillis.toMillis());
        this.cache.schedulePrune(delay.toMillis());
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
