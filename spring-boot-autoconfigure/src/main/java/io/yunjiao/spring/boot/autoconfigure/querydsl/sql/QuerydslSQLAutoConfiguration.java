package io.yunjiao.spring.boot.autoconfigure.querydsl.sql;

import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import io.yunjiao.spring.querydsl.sql.SQLQueryCurdExecutor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * QueryDSL SQL 自动配置， 包括：{@link SQLQueryFactory}
 *
 * @author yangyunjiao
 */
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({SQLQueryFactory.class, DataSource.class})
public class QuerydslSQLAutoConfiguration {
    private final DataSource dataSource;

    @PostConstruct
    public void postConstruct() {
        log.info("QueryDSL SQL Auto Configuration");
    }

    @Bean
    SQLQueryFactory sqlQueryFactory(SQLTemplates sqlTemplates,
                                    ObjectProvider<SQLQueryFactoryConfigurer> sqlQueryFactoryConfigurers) {
        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(sqlTemplates);
        configuration.setExceptionTranslator(new SpringExceptionTranslator());

        SQLQueryFactory bean = new SQLQueryFactory(
                configuration,
                new SpringConnectionProvider(dataSource) // 集成Spring事务
        );
        // 执行自定义配置
        sqlQueryFactoryConfigurers.orderedStream().forEach(configurer -> configurer.configure(bean));

        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [SQL Query Factory: {}]", bean);
        }
        return bean;
    }

    @Bean
    SQLQueryCurdExecutor sqlQueryCurdExecutor() {
        SQLQueryCurdExecutor bean = new SQLQueryCurdExecutor();

        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [SQLQuery Curd Executor: {}]", bean);
        }
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    SQLTemplates mysqlTemplates() {
        SQLTemplates bean = MySQLTemplates.builder().build();
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [MySQL Templates: {}]", bean);
        }
        return bean;
    }
}
