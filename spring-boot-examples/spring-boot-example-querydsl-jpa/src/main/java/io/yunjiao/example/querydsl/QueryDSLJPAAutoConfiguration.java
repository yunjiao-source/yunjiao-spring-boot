package io.yunjiao.example.querydsl;

import io.yunjiao.spring.boot.autoconfigure.querydsl.jpa.JPAQueryFactoryConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class QueryDSLJPAAutoConfiguration {
    @Bean
    JPAQueryFactoryConfigurer jpaQueryFactoryConfigurer() {
        return jpaQueryFactory -> {

        };
    }
}
