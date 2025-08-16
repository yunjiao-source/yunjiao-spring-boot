package io.yunjiao.spring.boot.autoconfigure.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.yunjiao.spring.boot.autoconfigure.querydsl.jpa.QuerydslJPAAutoConfiguration;
import io.yunjiao.spring.boot.autoconfigure.querydsl.jpa.JPAQueryFactoryConfigurer;
import io.yunjiao.spring.querydsl.jpa.JPAQueryCurdExecutor;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * ${@link QuerydslJPAAutoConfiguration} 单元测试用例
 *
 * @author yangyunjiao
 */
public class QuerydslJPAAutoConfigurationTest {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    public void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(QuerydslJPAAutoConfiguration.class));
    }

    @Test
    public void giveEntityManager_thenExist() {
        applicationContextRunner
                .withBean(EntityManager.class, () -> Mockito.mock(EntityManager.class))
                .withUserConfiguration(QuerydslJPAAutoConfigurationTest.ConfigurerConfiguration.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(JPAQueryFactory.class);
                    assertThat(context).hasSingleBean(JPAQueryFactoryConfigurer.class);
                    assertThat(context).hasSingleBean(JPAQueryCurdExecutor.class);
                });
    }

    @Configuration
    static class ConfigurerConfiguration {


        @Bean
        public JPAQueryFactoryConfigurer jpaQueryFactoryConfigurer() {
            return mock(JPAQueryFactoryConfigurer.class);
        }

    }
}
