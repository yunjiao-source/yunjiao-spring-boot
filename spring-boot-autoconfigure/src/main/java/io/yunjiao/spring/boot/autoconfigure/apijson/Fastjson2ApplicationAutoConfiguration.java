package io.yunjiao.spring.boot.autoconfigure.apijson;

import apijson.fastjson2.APIJSONApplication;
import io.yunjiao.spring.apijson.orm.IdKeyStrategy;
import io.yunjiao.spring.apijson.orm.NewIdStrategy;
import io.yunjiao.spring.boot.autoconfigure.apijson.condition.ApllicationCondition;
import io.yunjiao.spring.boot.autoconfigure.apijson.fastjson2.*;
import io.yunjiao.spring.boot.autoconfigure.util.PropertyNameConsts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

import javax.sql.DataSource;

/**
 * Fastjson2 应用自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@RequiredArgsConstructor
@AutoConfiguration(after = {ApijsonInitAutoConfiguration.class})
@Conditional(ApllicationCondition.OnFastjson2.class)
@ConditionalOnClass({APIJSONApplication.class})
public class Fastjson2ApplicationAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("Fastjson2 Application Auto Configuration");
    }

    /**
     * Fastjson2创建器 实例化
     * @param dataSource 数据源
     * @param sqlProperties sql配置属性
     * @return {@link Fastjson2Creator} 实例
     */
    @Bean
    @ConditionalOnMissingBean
    Fastjson2Creator fastjson2Creator(DataSource dataSource,
                                      ApijsonSqlProperties sqlProperties) {
        Fastjson2Creator bean = new Fastjson2Creator(dataSource, sqlProperties);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Fastjson2 Creator]");
        }
        return bean;
    }

    /**
     * fastjson2回调 实例化
     * @param idKeyStrategy 主键名称策略
     * @param newIdStrategy 主键生成策略
     * @return {@link Fastjson2SimpleCallback} 实例
     */
    @Bean
    @ConditionalOnMissingBean
    Fastjson2SimpleCallback fastjson2SimpleCallback(IdKeyStrategy idKeyStrategy,
                                                    NewIdStrategy newIdStrategy) {
        Fastjson2SimpleCallback bean = new Fastjson2SimpleCallback(idKeyStrategy, newIdStrategy);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Fastjson2 Simple Callback]");
        }
        return bean;
    }

    /**
     * Fastjson2 应用初始化Bean 实例化
     * @param fastjson2SimpleCallback fastjson2回调
     * @param fastjson2Creator Fastjson2创建器
     * @param properties  APIJSON 配置属性
     * @return @return {@link Fastjson2InitializingBean} 实例
     */
    @Bean
    Fastjson2InitializingBean fastjson2InitializingBean(Fastjson2SimpleCallback fastjson2SimpleCallback,
                                                        Fastjson2Creator fastjson2Creator,
                                                        ApijsonProperties properties) {
        Fastjson2InitializingBean bean = new Fastjson2InitializingBean(fastjson2SimpleCallback, fastjson2Creator, properties);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Fastjson2 Initializing Bean]");
        }
        return bean;
    }

    /**
     * rest api 自动配置
     */
    @RequiredArgsConstructor
    @AutoConfiguration
    @ConditionalOnProperty(name = {PropertyNameConsts.PROPERTY_PREFIX_APIJSON_RESTAPI_ENABLE},
            havingValue = "true",
            matchIfMissing = true)
    static
    class Fastjson2RestApiAutoConfiguration {
        private final ApijsonProperties properties;

        /**
         * rest api 接口
         *
         * @return {@link Fastjson2RestController}实例
         */
        @Bean
        Fastjson2RestController fastjson2RestController() {
            Fastjson2RestController bean = new Fastjson2RestController(properties);
            if (log.isDebugEnabled()) {
                log.debug("Configure Bean [Fastjson2 Rest Controller]");
            }
            return bean;
        }

        /**
         * rest api 扩展接口
         *
         * @return {@link Fastjson2EXtRestController}实例
         */
        @Bean
        Fastjson2EXtRestController fastjson2ExtRestController() {
            Fastjson2EXtRestController bean = new Fastjson2EXtRestController();
            if (log.isDebugEnabled()) {
                log.debug("Configure Bean [Fastjson2 Rest Ext Controller]");
            }
            return bean;
        }
    }

}
