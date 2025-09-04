package yunjiao.springboot.extension.captcha.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 使用Guava LoadingCache实现的验证码缓存器
 *
 * @author yangyunjiao
 */
@Slf4j
public class GuavaCaptchaCache implements CaptchaCache {
    /**
     * 缓存
     */
    private final LoadingCache<String, Optional<String>> cache;

    /**
     * 构造器
     *
     * @param expired 缓存过期时间
     * @param size 缓存大小
     */
    public GuavaCaptchaCache(Duration expired, long size) {
        CacheBuilder<Object, Object> builder =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(expired.toMillis(), TimeUnit.MILLISECONDS);

        if (size > 0) {
            builder.maximumSize(size);
        }

        this.cache = builder.build(new NullCacheLoader());
    }

    @Override
    public void put(String key, String value) {
        cache.put(key, Optional.ofNullable(value));
    }

    @Override
    public Optional<String> get(String key) {
        try {
            return cache.get(key);
        } catch (ExecutionException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void remove(String key) {
        cache.invalidate(key);
    }

    @Override
    public void clear() {
        cache.invalidateAll();
    }

    @Override
    public long size() {
        return cache.size();
    }

    private static final class NullCacheLoader extends CacheLoader<String, Optional<String>> implements Serializable {

        @Override
        @NonNull
        public Optional<String> load(@Nullable String key) throws Exception {
            return Optional.empty();
        }
    }
}
