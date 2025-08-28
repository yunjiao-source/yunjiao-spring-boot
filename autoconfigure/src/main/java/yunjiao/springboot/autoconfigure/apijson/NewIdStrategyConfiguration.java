package yunjiao.springboot.autoconfigure.apijson;

import cn.hutool.core.lang.Snowflake;
import yunjiao.springboot.autoconfigure.apijson.condition.NewIdStrategyCondition;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import yunjiao.springboot.extension.apjson.orm.*;

/**
 * {@link NewIdStrategy}实现类自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({NewIdStrategy.class})
public class NewIdStrategyConfiguration {

    /**
     * {@link PostConstruct} 注解方法
     */
    @PostConstruct
    public void postConstruct() {
        log.info("New Id Strategy Configuration");
    }

    @Bean
    @Conditional(NewIdStrategyCondition.OnDatabase.class)
    NewIdStrategy newIdDatabaseStrategy() {
        NewIdStrategy bean = new NewIdDatabaseStrategy();
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [New Id Database Strategy: {}]", bean);
        }
        return bean;
    }

    @Bean
    @Conditional(NewIdStrategyCondition.OnSnowflake.class)
    NewIdStrategy newIdSnowflakeStrategy(Snowflake snowflake) {
        NewIdStrategy bean = new NewIdSnowflakeStrategy(snowflake);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [New Id Snowflake Strategy: {}]", bean);
        }
        return bean;
    }

    @Bean
    @Conditional(NewIdStrategyCondition.OnTimestamp.class)
    NewIdStrategy newIdTimestampStrategy() {
        NewIdStrategy bean = new NewIdTimestampStrategy();
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [New Id Timestamp Strategy: {}]", bean);
        }
        return bean;
    }

    @Bean
    @Conditional(NewIdStrategyCondition.OnUuid.class)
    NewIdStrategy newIdUuidStrategy() {
        NewIdStrategy bean = new NewIdUuidStrategy();
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [New Id UUID Strategy: {}]", bean);
        }
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    @Conditional(NewIdStrategyCondition.OnCustom.class)
    NewIdStrategy newIdCustomStrategy() {
        NewIdStrategy bean = new NewIdExceptionStrategy();
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [New Id Exception Strategy: {}]", bean);
        }
        return bean;
    }
}
