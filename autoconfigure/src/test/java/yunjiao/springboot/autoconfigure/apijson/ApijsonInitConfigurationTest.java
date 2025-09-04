package yunjiao.springboot.autoconfigure.apijson;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yunjiao.springboot.extension.apijson.ApijsonFunctionParserConfigurer;
import yunjiao.springboot.extension.apijson.ApijsonSqlConfigConfigurer;
import yunjiao.springboot.extension.apijson.ApijsonVerifierConfigurer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * {@link ApijsonInitConfiguration} 单元测试用例
 *
 * @author yangyunjiao
 */
public class ApijsonInitConfigurationTest {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(ApijsonInitConfiguration.class, TestConfiguration.class));
    }

    @Test
    void givenNoConfigurer_whenConfig() {
        applicationContextRunner.run(context -> {
            assertThat(context).doesNotHaveBean(ApijsonSqlConfigConfigurer.class);
            assertThat(context).doesNotHaveBean(ApijsonVerifierConfigurer.class);
            assertThat(context).doesNotHaveBean(ApijsonFunctionParserConfigurer.class);
            assertThat(context).hasSingleBean(ApijsonInitializingBean.class);
        });
    }

    @Test
    void givenConfigurer_whenConfig() {
        applicationContextRunner
                .withUserConfiguration(ConfigurerConfiguration.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(ApijsonSqlConfigConfigurer.class);
                    assertThat(context).hasSingleBean(ApijsonVerifierConfigurer.class);
                    assertThat(context).hasSingleBean(ApijsonFunctionParserConfigurer.class);
                    assertThat(context).hasSingleBean(ApijsonInitializingBean.class);
                });
    }

    @Configuration
    @EnableConfigurationProperties({ApijsonProperties.class, ApijsonSqlProperties.class,
            ApijsonParserProperties.class, ApijsonVerifierProperties.class})
    static class TestConfiguration {

    }

    @Configuration
    static class ConfigurerConfiguration {
        @Bean
        public ApijsonSqlConfigConfigurer apijsonSqlConfigConfigurer() {
            return mock(ApijsonSqlConfigConfigurer.class);
        }

        @Bean
        public ApijsonVerifierConfigurer apijsonVerifierConfigurer() {
            return mock(ApijsonVerifierConfigurer.class);
        }

        @Bean
        public ApijsonFunctionParserConfigurer apijsonFunctionParserConfigurer() {
            return mock(ApijsonFunctionParserConfigurer.class);
        }
    }
}
