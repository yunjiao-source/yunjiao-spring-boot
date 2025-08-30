package yunjiao.springboot.extension.captcha;

import org.junit.jupiter.api.Test;
import yunjiao.springboot.extension.captcha.cache.CaptchaCache;
import yunjiao.springboot.extension.captcha.cache.CaptchaCacheFactory;
import yunjiao.springboot.extension.captcha.cache.HutoolCaptchaCache;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link HutoolCaptchaCache} 单元测试用例
 *
 * @author yangyunjiao
 */
public class HutoolCaptcheCacheTest {
    private static final String NAME = "name";
    private static final String VALUE = "张三";

    private final CaptchaCache cache = CaptchaCacheFactory.initHtoolCache(Duration.ofSeconds(2L));

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
