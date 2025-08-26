package yunjiao.springboot.autoconfigure.captcha;

import yunjiao.springboot.extension.captcha._Captcha;
import yunjiao.springboot.extension.common.captcha.CaptchaService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Map;

/**
 * 验证码 自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass({_Captcha.class})
@Import({
        HutoolCaptchaConfiguration.class
})
public class CaptchaAutoConfiguration {
    /**
     * {@link PostConstruct} 注解方法
     */
    @PostConstruct
    public void postConstruct() {
        log.info("Captcha Auto Configuration");
    }

    @Bean
    CaptchaServiceFactory captchaServiceFactory(Map<String, CaptchaService> services) {
        CaptchaServiceFactory factory = new CaptchaServiceFactory(services);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Captcha Service Factory: {}]", factory);
        }
        return factory;
    }
}
