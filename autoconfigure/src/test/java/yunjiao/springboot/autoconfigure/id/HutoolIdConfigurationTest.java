package yunjiao.springboot.autoconfigure.id;

import cn.hutool.core.lang.Snowflake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import yunjiao.springboot.extension.common.CommonConsts;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link HutoolIdConfiguration} 单元测试用例
 *
 * @author yangyunjiao
 */
public class HutoolIdConfigurationTest {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    public void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(HutoolIdConfiguration.class));
    }

    @Test
    public void whenConfig_thenExist() {
        applicationContextRunner
                .run(context -> {
                    assertThat(context).hasSingleBean(Snowflake.class);
                });
    }

    @Test
    public void giveEnv_whenConfig_thenOK() {
        System.setProperty(CommonConsts.ENV_SNOWFLAKE_DATACENTER_ID, "2");
        System.setProperty(CommonConsts.ENV_SNOWFLAKE_WORKER_ID, "3");
        applicationContextRunner
                .run(context -> {
                    assertThat(context).hasSingleBean(Snowflake.class);
                    Snowflake bean = context.getBean(Snowflake.class);
                    long id = bean.nextId();
                    assertThat(bean.getDataCenterId(id)).isEqualTo(2);
                    assertThat(bean.getWorkerId(id)).isEqualTo(3);
                });
    }
}
