package yunjiao.springboot.autoconfigure.querydsl.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import yunjiao.springboot.extension.querydsl.jpa.JPAQueryCurdExecutor;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QueryDSL JPA 自动配置， 包括：{@link JPAQueryFactory}
 *
 * @author yangyunjiao
 */
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({JPAQueryFactory.class})
public class QuerydslJPAConfiguration {
    private final EntityManager entityManager;

    /**
     * {@link PostConstruct} 注解方法
     */
    @PostConstruct
    public void postConstruct() {
        log.info("QueryDSL JPA Configuration");
    }

    /**
     * {@link JPAQueryFactory} 创建实例
     * @param jpaQueryFactoryConfigurers 自定义配置
     * @return 实例
     */
    @Bean
    public JPAQueryFactory jpaQueryFactory(ObjectProvider<JPAQueryFactoryConfigurer> jpaQueryFactoryConfigurers) {
        JPAQueryFactory bean = new JPAQueryFactory(entityManager);
        // 执行自定义配置
        jpaQueryFactoryConfigurers.orderedStream().forEach(configurer -> configurer.configure(bean));

        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [JPA Query Factory -> {}]", bean);
        }
        return bean;
    }

    @Bean
    JPAQueryCurdExecutor sqlQueryCurdExecutor() {
        JPAQueryCurdExecutor bean = new JPAQueryCurdExecutor();

        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [JPAQuery Curd Executor -> {}]", bean);
        }
        return bean;
    }
}
