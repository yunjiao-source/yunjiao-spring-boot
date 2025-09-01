package yunjiao.springboot.autoconfigure;

import cn.hutool.extra.spring.SpringUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yunjiao.springboot.autoconfigure.CommonAutoConfig;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link CommonAutoConfig} 单元测试用例
 *
 * @author yangyunjiao
 */
public class CommonAutoConfigTest {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(CommonAutoConfig.class, TestConfig.class));
    }

    @Test
    void givenDefault_thenConfig() {
        applicationContextRunner
                .run(context -> {
                    assertThat(context).hasSingleBean(SpringUtil.class);
                    assertThat(context).hasSingleBean(TestService.class);
                });
    }

    @Test
    void givenDefault_whenGetBean_thenExist() {
        applicationContextRunner
                .run(context -> {
                    TestService service = SpringUtil.getBean(TestService.class);
                    assertThat(service).isNotNull();
                    assertThat(service.say()).isEqualTo("Hello");
                });

    }

    static class TestService {
        public String say() {
            return "Hello";
        }
    }

    @Configuration
    static class TestConfig {
        @Bean
        TestService TestService() {
            return new TestService();
        }
    }

}
