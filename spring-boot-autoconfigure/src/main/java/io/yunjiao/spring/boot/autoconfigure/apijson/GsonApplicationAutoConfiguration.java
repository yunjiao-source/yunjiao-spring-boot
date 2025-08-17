package io.yunjiao.spring.boot.autoconfigure.apijson;

import apijson.gson.APIJSONApplication;
import io.yunjiao.spring.apijson.orm.IdKeyStrategy;
import io.yunjiao.spring.apijson.orm.NewIdStrategy;
import io.yunjiao.spring.boot.autoconfigure.apijson.condition.ApllicationCondition;
import io.yunjiao.spring.boot.autoconfigure.apijson.gson.*;
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
 * Gson 应用自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@RequiredArgsConstructor
@AutoConfiguration(after = {ApijsonInitAutoConfiguration.class})
@Conditional(ApllicationCondition.OnGson.class)
@ConditionalOnClass({APIJSONApplication.class})
public class GsonApplicationAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("Gson Application Auto Configuration");
    }

    /**
     * Gson创建器 实例化
     * @param dataSource 数据源
     * @param sqlProperties sql配置属性
     * @return {@link GsonCreator} 实例
     */
    @Bean
    @ConditionalOnMissingBean
    GsonCreator gsonCreator(DataSource dataSource,
                            ApijsonSqlProperties sqlProperties) {
        GsonCreator bean = new GsonCreator(dataSource, sqlProperties);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Gson Creator]");
        }
        return bean;
    }

    /**
     * Gson回调 实例化
     * @param idKeyStrategy 主键名称策略
     * @param newIdStrategy 主键生成策略
     * @return {@link GsonSimpleCallback} 实例
     */
    @Bean
    @ConditionalOnMissingBean
    GsonSimpleCallback gsonSimpleCallback(IdKeyStrategy idKeyStrategy,
                                          NewIdStrategy newIdStrategy) {
        GsonSimpleCallback bean = new GsonSimpleCallback(idKeyStrategy, newIdStrategy);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Gson Simple Callback]");
        }
        return bean;
    }

    /**
     * Gson 应用初始化Bean 实例化
     * @param gsonSimpleCallback fastjson2回调
     * @param gsonCreator Fastjson2创建器
     * @param properties  APIJSON 配置属性
     * @return @return {@link GsonInitializingBean} 实例
     */
    @Bean
    GsonInitializingBean gsonInitializingBean(GsonSimpleCallback gsonSimpleCallback,
                                              GsonCreator gsonCreator,
                                              ApijsonProperties properties) {
        GsonInitializingBean bean = new GsonInitializingBean(gsonSimpleCallback, gsonCreator, properties);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Gson Initializing Bean]");
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
    class GsonRestApiAutoConfiguration {
        private final ApijsonProperties properties;

        @Bean
        GsonRestController gsonRestController() {
            GsonRestController bean = new GsonRestController(properties);
            if (log.isDebugEnabled()) {
                log.debug("Configure Bean [Gson Rest Controller]");
            }
            return bean;
        }

        @Bean
        GsonEXtRestController gsonEXtRestController() {
            GsonEXtRestController bean = new GsonEXtRestController();
            if (log.isDebugEnabled()) {
                log.debug("Configure Bean [Gson Ext Rest Controller]");
            }
            return bean;
        }
    }
}
