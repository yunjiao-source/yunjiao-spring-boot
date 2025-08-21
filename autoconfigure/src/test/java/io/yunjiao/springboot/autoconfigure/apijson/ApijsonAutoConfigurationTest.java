package io.yunjiao.springboot.autoconfigure.apijson;

import io.yunjiao.extension.apjson.orm.IdKeyApijsonStrategy;
import io.yunjiao.extension.apjson.orm.IdKeyStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * {@link NewIdStrategyAutoConfiguration}单元测试用例
 *
 * @author yangyunjiao
 */
public class ApijsonAutoConfigurationTest {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    public void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(ApijsonAutoConfiguration.class));
    }

    @Test
    public void shouldAutoConfigurationApplied() {
        applicationContextRunner
                .run(context -> {
                    assertThat(context).hasSingleBean(IdKeyStrategy.class);
                    IdKeyStrategy strategy = context.getBean(IdKeyStrategy.class);
                    assertInstanceOf(IdKeyApijsonStrategy.class, strategy);

                    assertThat(context).hasSingleBean(WebMvcConfigurer.class);
                    assertThat(context).hasSingleBean(ApijsonProperties.class);
                    assertThat(context).hasSingleBean(ApijsonSqlProperties.class);
                    assertThat(context).hasSingleBean(ApijsonParserProperties.class);
                    assertThat(context).hasSingleBean(ApijsonVerifierProperties.class);
                });
    }



}
