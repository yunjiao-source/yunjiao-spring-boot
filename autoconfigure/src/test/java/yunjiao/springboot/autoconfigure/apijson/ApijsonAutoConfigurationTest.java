package yunjiao.springboot.autoconfigure.apijson;

import yunjiao.springboot.extension.apijson.orm.IdKeyApijsonStrategy;
import yunjiao.springboot.extension.apijson.orm.IdKeyStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * {@link NewIdStrategyConfiguration}单元测试用例
 *
 * @author yangyunjiao
 */
public class ApijsonAutoConfigurationTest {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(ApijsonAutoConfiguration.class));
    }

    @Test
    void shouldAutoConfigurationApplied() {
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
