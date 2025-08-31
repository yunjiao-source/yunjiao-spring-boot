package yunjiao.springboot.autoconfigure.id;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import yunjiao.springboot.extension.common.CommonConsts;
import yunjiao.springboot.extension.common.util.Utils;

/**
 * 基于Hutool框架的自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@ConditionalOnClass({Snowflake.class})
@Configuration(proxyBeanMethods = false)
public class HutoolIdConfiguration {
    /**
     * {@link PostConstruct} 注解方法
     */
    @PostConstruct
    public void postConstruct() {
        log.info("Hutool Id Configuration");
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
        long workerId = Utils.convertEnv(env, CommonConsts.ENV_SNOWFLAKE_WORKER_ID, Long.class, 1L);
        long datacenterId = Utils.convertEnv(env, CommonConsts.ENV_SNOWFLAKE_DATACENTER_ID, Long.class, 1L);

        Snowflake snowflake = IdUtil.getSnowflake(workerId, datacenterId);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Snowflake -> {}], workerId={}, datacenterId={}", snowflake, workerId, datacenterId);
        }

        if (workerId == 1L &&  datacenterId == 1L) {
            log.warn("Hutool 框架雪花算法配置使用默认参数。如需支持分布式，请设置系统环境变量：{} 与 {}", CommonConsts.ENV_SNOWFLAKE_WORKER_ID, CommonConsts.ENV_SNOWFLAKE_DATACENTER_ID);
        }
        return snowflake;
    }

}
