package yunjiao.springboot.autoconfigure.id;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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
public class HutoolIdConfiguration {
    /**
     * {@link PostConstruct} 注解方法
     */
    @PostConstruct
    public void postConstruct() {
        log.info("Hutool Id Auto Configuration");
    }

    /**
     * 雪花算法, 获取系统变量 SNOWFLAKE_WORKER_ID 和 SNOWFLAKE_DATACENTER_ID 值创建,
     * 如果不存在，值默认是1
     *
     * @param env 必须值
     * @return 实例
     */
    @Bean
    @ConditionalOnClass({Snowflake.class})
    public Snowflake snowflake(Environment env) {
        final String SNOWFLAKE_WORKER_ID = "SNOWFLAKE_WORKER_ID";
        final String SNOWFLAKE_DATACENTER_ID = "SNOWFLAKE_DATACENTER_ID";

        if (log.isDebugEnabled()) {
            log.debug("正在配置雪花算法，默认workerId=1，datacenterId=1。如需支持分布式，请设置系统环境变量：{} 与 {}", SNOWFLAKE_WORKER_ID, SNOWFLAKE_DATACENTER_ID);
        }

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
            log.debug("Configure Bean [Snowflake: {}], workerId={}, datacenterId={}", snowflake, workerId, datacenterId);
        }
        return snowflake;
    }
}
