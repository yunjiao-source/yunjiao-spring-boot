package io.yunjiao.springboot.autoconfigure.hutool;

import cn.hutool.core.lang.Snowflake;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * 基于Hutool框架的自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass({Snowflake.class})
@EnableConfigurationProperties({HutoolProperties.class})
@Import({
        SnowflakeAutoConfiguration.class
})
public class HutoolAutoConfiguration {

    /**
     * {@link PostConstruct} 注解方法
     */
    @PostConstruct
    public void postConstruct() {
        log.info("Hutool Auto Configuration");
    }

}
