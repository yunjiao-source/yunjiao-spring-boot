package io.yunjiao.example.querydsl;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 自动配置
 *
 * @author yangyunjiao
 */
@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class QueryDSLJPAAutoConfiguration {
}
