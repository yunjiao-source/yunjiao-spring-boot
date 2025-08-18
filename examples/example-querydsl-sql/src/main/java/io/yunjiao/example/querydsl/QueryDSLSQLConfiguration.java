package io.yunjiao.example.querydsl;

import com.querydsl.sql.SQLTemplates;
import io.yunjiao.springboot.autoconfigure.querydsl.sql.SQLQueryFactoryConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@Configuration
@EnableTransactionManagement
public class QueryDSLSQLConfiguration {
    @Bean
    SQLQueryFactoryConfigurer sqlQueryFactoryConfigurer() {
        return sqlQueryFactory -> {
            com.querydsl.sql.Configuration config = sqlQueryFactory.getConfiguration();
            log.info("配置：Configuration={}", config);

            SQLTemplates template = config.getTemplates();
            log.info("配置：SQLTemplates={}", template);
        };
    }
}
