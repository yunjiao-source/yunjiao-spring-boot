package yunjiao.springboot.autoconfigure.id;

import cc.siyecao.uid.core.resposity.WorkerIdAssigner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yunjiao.springboot.extension.common.CommonConsts;
import yunjiao.springboot.extension.id.uidgenerator.UidGeneratorCached;
import yunjiao.springboot.extension.id.uidgenerator.UidGeneratorDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link UidGeneratorConfiguration} 单元测试用例
 *
 * @author yangyunjiao
 */
@Slf4j
public class UidGeneratorConfigurationTest {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(UidGeneratorConfiguration.class));
    }

    @Test
    void givenDefault_whenConfig_thenExist() {
        applicationContextRunner
                .run(context -> {
                    assertThat(context).hasSingleBean(WorkerIdAssigner.class);
                    assertThat(context).hasSingleBean(UidGeneratorDefault.class);
                    assertThat(context).hasSingleBean(UidGeneratorCached.class);
                    assertThat(context).doesNotHaveBean(CachedUidGeneratorConfigurer.class);
                });
    }

    @Test
    void giveWorkId_whenConfig_thenOk() {
        applicationContextRunner
                .withPropertyValues(CommonConsts.ENV_SNOWFLAKE_WORKER_ID+ "=2")
                .run(context -> {
                    UidGeneratorDefault uidGeneratorDefault = context.getBean(UidGeneratorDefault.class);
                    long id = uidGeneratorDefault.getUID();
                    assertThat(uidGeneratorDefault.parseUID(id)).contains("\"workerId\":\"2\"");

                    UidGeneratorCached uidGeneratorCached = context.getBean(UidGeneratorCached.class);
                    id = uidGeneratorCached.getUID();
                    assertThat(uidGeneratorCached.parseUID(id)).contains("\"workerId\":\"2\"");
                });
    }

    @Test
    void givenCachedUidGeneratorBean_whenConfig_thenExist() {
        applicationContextRunner.withConfiguration(AutoConfigurations.of(TestConfiguration.class))
                .run(context -> {
                    assertThat(context).hasSingleBean(CachedUidGeneratorConfigurer.class);
                });
    }

    @Configuration
    static class TestConfiguration {
        @Bean
        CachedUidGeneratorConfigurer configurer() {
            return generator -> log.info("===configurer:{}===", generator);
        }

    }

}
