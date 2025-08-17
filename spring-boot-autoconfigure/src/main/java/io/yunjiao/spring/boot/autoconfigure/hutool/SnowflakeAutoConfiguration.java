package io.yunjiao.spring.boot.autoconfigure.hutool;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import io.yunjiao.spring.boot.autoconfigure.util.PropertyNameConsts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 基于Hutool框架的自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({Snowflake.class})
public class SnowflakeAutoConfiguration {
    @PostConstruct
    public void postConstruct() {
        log.info("Hutool Snowflake Auto Configuration");
    }

    /**
     * 雪花算法, 获取系统变量 SNOWFLAKE_WORKER_ID 和 SNOWFLAKE_DATACENTER_ID 值创建,
     * 如果不存在，值默认是1
     *
     * @return 实例
     */
    @Bean
    @ConditionalOnProperty(
            prefix = PropertyNameConsts.PROPERTY_PREFIX_HUTOOL,
            name = "snowflake",
            havingValue = "true",
            matchIfMissing = true
    )
    public Snowflake snowflake(Environment env) {
        final String SNOWFLAKE_WORKER_ID = "SNOWFLAKE_WORKER_ID";
        final String SNOWFLAKE_DATACENTER_ID = "SNOWFLAKE_DATACENTER_ID";

        long workerId = 1L;
        try {
            String workIdEnv = env.getProperty(SNOWFLAKE_WORKER_ID);
            workerId = Long.parseLong(workIdEnv);
        } catch (Exception ignored) {

        }

        long datacenterId = 1L;
        try {
            String datacenterIdEnv = env.getProperty(SNOWFLAKE_DATACENTER_ID);
            datacenterId = Long.parseLong(datacenterIdEnv);
        } catch (Exception ignored) {

        }

        Snowflake snowflake = IdUtil.getSnowflake(workerId, datacenterId);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Snowflake], workerId={}, datacenterId={}", workerId, datacenterId);
        }
        return snowflake;
    }
}
