package io.yunjiao.spring.boot.autoconfigure.apijson;

import cn.hutool.core.lang.Snowflake;
import io.yunjiao.spring.apijson.orm.*;
import io.yunjiao.spring.boot.autoconfigure.apijson.condition.NewIdStrategyCondition;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

/**
 * {@link NewIdStrategy}实现类自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@AutoConfiguration
public class NewIdStrategyAutoConfiguration {
    @PostConstruct
    public void postConstruct() {
        log.info("New Id Strategy Auto Configuration");
    }

    @Bean
    @Conditional(NewIdStrategyCondition.OnDatabase.class)
    NewIdStrategy newIdDatabaseStrategy() {
        NewIdStrategy bean = new NewIdDatabaseStrategy();
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [New Id Database Strategy]");
        }
        return bean;
    }

    @Bean
    @Conditional(NewIdStrategyCondition.OnSnowflake.class)
    NewIdStrategy newIdSnowflakeStrategy(Snowflake snowflake) {
        NewIdStrategy bean = new NewIdSnowflakeStrategy(snowflake);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [New Id Snowflake Strategy]");
        }
        return bean;
    }

    @Bean
    @Conditional(NewIdStrategyCondition.OnTimestamp.class)
    NewIdStrategy newIdTimestampStrategy() {
        NewIdStrategy bean = new NewIdTimestampStrategy();
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [New Id Timestamp Strategy]");
        }
        return bean;
    }

    @Bean
    @Conditional(NewIdStrategyCondition.OnUuid.class)
    NewIdStrategy newIdUuidStrategy() {
        NewIdStrategy bean = new NewIdUuidStrategy();
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [New Id UUID Strategy]");
        }
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    @Conditional(NewIdStrategyCondition.OnCustom.class)
    NewIdStrategy newIdCustomStrategy() {
        NewIdStrategy bean = new NewIdExceptionStrategy();
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [New Id Exception Strategy]");
        }
        return bean;
    }
}
