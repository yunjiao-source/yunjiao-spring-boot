package io.yunjiao.springboot.autoconfigure.querydsl;

import com.querydsl.sql.H2Templates;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import io.yunjiao.extension.querydsl.sql.SQLQueryCurdExecutor;
import io.yunjiao.springboot.autoconfigure.querydsl.sql.QuerydslSQLAutoConfiguration;
import io.yunjiao.springboot.autoconfigure.querydsl.sql.SQLQueryFactoryConfigurer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * ${@link QuerydslSQLAutoConfiguration} 单元测试用例
 *
 * @author yangyunjiao
 */
public class QuerydslSQLAutoConfigurationTest {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    public void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(QuerydslSQLAutoConfiguration.class));
    }

    @Test
    public void giveDataSource_thenExist() {
        applicationContextRunner
                .withBean(DataSource.class, () -> mock(DataSource.class))
                .withUserConfiguration(TestConfiguration.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(SQLQueryFactory.class);
                    assertThat(context).hasSingleBean(SQLQueryCurdExecutor.class);
                    assertThat(context).hasSingleBean(SQLTemplates.class)
                            .getBean(SQLTemplates.class)
                            .isInstanceOf((MySQLTemplates.class));
                    assertThat(context).hasSingleBean(SQLQueryFactoryConfigurer.class);
                });
    }

    @Test
    public void giveH2Template_thenUsed() {
        applicationContextRunner
                .withBean(DataSource.class, () -> mock(DataSource.class))
                .withUserConfiguration(NewSQLTemplateConfiguration.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(SQLTemplates.class)
                            .getBean(SQLTemplates.class)
                            .isInstanceOf((H2Templates.class));
                });
    }

    @Configuration
    static class NewSQLTemplateConfiguration {
        @Bean
        SQLTemplates H2Templates() {
            return H2Templates.builder().build();
        }
    }

    @Configuration
    static class TestConfiguration {
        @Bean
        SQLQueryFactoryConfigurer sqlQueryFactoryConfigurer() {
            return mock(SQLQueryFactoryConfigurer.class);
        }

    }
}
