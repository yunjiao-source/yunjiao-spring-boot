package yunjiao.springboot.example.captcha;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import yunjiao.springboot.extension.captcha.cache.CaptchaCacheFactory;

import java.time.Duration;

/**
 * 配置
 *
 * @author yangyunjiao
 */
@Slf4j
@Configuration
public class CaptchaConfiguration {

    @PostConstruct
    void postConstruct() {
        // 设置过期时间
        CaptchaCacheFactory.initHtoolCache(Duration.ofSeconds(30));
    }

}
