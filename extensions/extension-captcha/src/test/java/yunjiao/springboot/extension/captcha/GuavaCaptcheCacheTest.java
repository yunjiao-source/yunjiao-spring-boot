package yunjiao.springboot.extension.captcha;

import org.junit.jupiter.api.Test;
import yunjiao.springboot.extension.captcha.cache.CaptchaCache;
import yunjiao.springboot.extension.captcha.cache.CaptchaCacheFactory;
import yunjiao.springboot.extension.captcha.cache.GuavaCaptchaCache;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link GuavaCaptchaCache} 单元测试用例
 *
 * @author yangyunjiao
 */
public class GuavaCaptcheCacheTest {
    private static final String NAME = "name";
    private static final String VALUE = "张三";

    private final CaptchaCache cache = CaptchaCacheFactory.initGuavaCache(Duration.ofSeconds(2L), 10L);

    @Test
    void givenCache_whenGetInTime_thenExist() throws InterruptedException {
        cache.put(NAME, VALUE);
        assertThat(cache.get(NAME)).hasValue(VALUE);
    }

    @Test
    void givenCache_whenGetOutOfTime_thenEmpty() throws InterruptedException {
        cache.put(NAME, VALUE);
        TimeUnit.SECONDS.sleep(3L);
        assertThat(cache.get(NAME)).isEmpty();
    }

    @Test
    void giveWrongName_whenGet_thenEmpty() {
        assertThat(cache.get("123")).isEmpty();
    }
}
