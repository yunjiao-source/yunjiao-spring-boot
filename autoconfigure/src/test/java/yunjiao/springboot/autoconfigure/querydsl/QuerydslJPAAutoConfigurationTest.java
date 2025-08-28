package yunjiao.springboot.autoconfigure.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import yunjiao.springboot.extension.querydsl.jpa.JPAQueryCurdExecutor;
import yunjiao.springboot.autoconfigure.querydsl.jpa.JPAQueryFactoryConfigurer;
import yunjiao.springboot.autoconfigure.querydsl.jpa.QuerydslJPAAutoConfiguration;
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
    public void givenEntityManager_thenConfig() {
        applicationContextRunner
                .withBean(EntityManager.class, () -> Mockito.mock(EntityManager.class))
                .withUserConfiguration(TestConfiguration.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(JPAQueryFactory.class);
                    assertThat(context).hasSingleBean(JPAQueryFactoryConfigurer.class);
                    assertThat(context).hasSingleBean(JPAQueryCurdExecutor.class);
                });
    }

    @Configuration
    static class TestConfiguration {


        @Bean
        public JPAQueryFactoryConfigurer jpaQueryFactoryConfigurer() {
            return mock(JPAQueryFactoryConfigurer.class);
        }

    }
}
