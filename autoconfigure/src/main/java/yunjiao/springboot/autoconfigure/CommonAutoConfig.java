package yunjiao.springboot.autoconfigure;

import cn.hutool.extra.spring.SpringUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import yunjiao.springboot.extension.common._Common;

/**
 * 通用的自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass({_Common.class})
public class CommonAutoConfig {
    /**
     * {@link PostConstruct} 注解方法
     */
    @PostConstruct
    public void postConstruct() {
        log.info("Common Auto Configuration");
    }

    @Bean
    SpringUtil springUtil() {
        SpringUtil bean = new SpringUtil();
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Spring Util -> {}]", bean);
        }
        return bean;
    }
}
