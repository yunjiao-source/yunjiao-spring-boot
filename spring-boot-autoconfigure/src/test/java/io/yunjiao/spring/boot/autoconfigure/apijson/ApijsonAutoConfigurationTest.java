package io.yunjiao.spring.boot.autoconfigure.apijson;

import io.yunjiao.spring.apijson.orm.IdKeyApijsonStrategy;
import io.yunjiao.spring.apijson.orm.IdKeyStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

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
    public void testNewIdSnowflakeStrategy() {
        applicationContextRunner
                .withBean(DataSource.class, () -> Mockito.mock(DataSource.class))
                .run(context -> {
                    assertThat(context).hasSingleBean(IdKeyStrategy.class);
                    IdKeyStrategy strategy = context.getBean(IdKeyStrategy.class);
                    assertInstanceOf(IdKeyApijsonStrategy.class, strategy);
                });
    }



}
