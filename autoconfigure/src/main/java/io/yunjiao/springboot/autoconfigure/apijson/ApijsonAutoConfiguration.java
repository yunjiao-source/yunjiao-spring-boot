package io.yunjiao.springboot.autoconfigure.apijson;


import io.yunjiao.extension.apjson.annotation.ApijsonRest;
import io.yunjiao.extension.apjson.orm.IdKeyApijsonStrategy;
import io.yunjiao.extension.apjson.orm.IdKeyStrategy;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * APIJSON 自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass({IdKeyApijsonStrategy.class})
@RequiredArgsConstructor
@EnableConfigurationProperties({ApijsonProperties.class, ApijsonSqlProperties.class,
        ApijsonParserProperties.class, ApijsonVerifierProperties.class})
@Import({
        NewIdStrategyAutoConfiguration.class,
        ApijsonInitAutoConfiguration.class,
        Fastjson2ApplicationAutoConfiguration.class,
        GsonApplicationAutoConfiguration.class
})
public class ApijsonAutoConfiguration {
    /**
     * sql属性
     */
    private final ApijsonProperties apijsonProperties;

    /**
     * {@link PostConstruct} 注解方法
     */
    @PostConstruct
    public void postConstruct() {
        log.info("APIJSON Auto Configuration");
    }

    /**
     * 主键名策略
     * @return 实例
     * @see IdKeyApijsonStrategy
     */
    @Bean
    @ConditionalOnMissingBean
    IdKeyStrategy idKeyApijsonStrategy() {
        IdKeyStrategy bean = new IdKeyApijsonStrategy();
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Id Key APIJSON Strategy]");
        }
        return bean;
    }


    /**
     * 配置rest api访问前缀
     * @return {@link WebMvcConfigurer} 实例
     */
    @Bean
    public WebMvcConfigurer configurePathMatch() {
        // rest接口统一前缀
        return new WebMvcConfigurer() {
            @Override
            public void configurePathMatch(@Nonnull PathMatchConfigurer configurer) {
                configurer.addPathPrefix(apijsonProperties.getRestApi().getPrefix(),
                        c -> c.isAnnotationPresent(ApijsonRest.class));
            }
        };
    }
}
