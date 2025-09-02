package yunjiao.springboot.autoconfigure.tika;

import org.apache.tika.Tika;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import yunjiao.springboot.autoconfigure.util.PropertyNameConsts;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link TikaAutoConfiguration} 单元测试用例
 *
 * @author yangyunjiao
 */
public class TikaAutoConfigurationTest {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(TikaAutoConfiguration.class));
    }

    @Test
    void whenConfig_thenExist() {
        applicationContextRunner
                .run(context -> {
                    assertThat(context).hasSingleBean(Tika.class);
                });
    }

    @Test
    void giveConfigFile_whenConfig_thenFail() {
        // 因为缺少包，所以异常，异常也说明配置文件正常加载了
        applicationContextRunner
                .withPropertyValues(PropertyNameConsts.PROPERTY_PREFIX_TIKA + ".config-xml-file=classpath:tika/config.xml")
                .run(context -> {
                    assertThat(context).hasFailed();
                    assertThat(context.getStartupFailure()).isNotNull();
                    assertThat(context.getStartupFailure())
                            .rootCause()
                            .isInstanceOf(ClassNotFoundException.class)
                            .hasMessageContaining("org.apache.tika.parser.html.HtmlEncodingDetector");
                });
    }
}
