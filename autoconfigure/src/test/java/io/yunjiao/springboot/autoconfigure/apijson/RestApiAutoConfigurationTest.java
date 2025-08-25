package io.yunjiao.springboot.autoconfigure.apijson;

import io.yunjiao.springboot.autoconfigure.apijson.fastjson2.Fastjson2EXtRestController;
import io.yunjiao.springboot.autoconfigure.apijson.fastjson2.Fastjson2RestController;
import io.yunjiao.springboot.autoconfigure.apijson.gson.GsonEXtRestController;
import io.yunjiao.springboot.autoconfigure.apijson.gson.GsonRestController;
import io.yunjiao.springboot.autoconfigure.util.PropertyNameConsts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * rest api 接口单元测试用例
 *
 * @author yangyunjiao
 */
public class RestApiAutoConfigurationTest {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    public void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(ApijsonAutoConfiguration.class));
    }

    @Test
    public void givenPropertyRestApiEnable_whenTrue_thenFastjson2Config() {
        applicationContextRunner
                .withPropertyValues(PropertyNameConsts.PROPERTY_APIJSON_RESTAPI_ENABLE + "=true")
                .withUserConfiguration(Fastjson2ApplicationConfiguration.Fastjson2RestApiAutoConfiguration.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(Fastjson2RestController.class);
                    assertThat(context).hasSingleBean(Fastjson2EXtRestController.class);
                });
    }

    @Test
    public void givenPropertyRestApiEnable_whenFalse_thenFastjson2Config() {
        applicationContextRunner
                .withPropertyValues(PropertyNameConsts.PROPERTY_APIJSON_RESTAPI_ENABLE + "=false")
                .withUserConfiguration(Fastjson2ApplicationConfiguration.Fastjson2RestApiAutoConfiguration.class)
                .run(context -> {
                    assertThat(context).doesNotHaveBean(Fastjson2RestController.class);
                    assertThat(context).doesNotHaveBean(Fastjson2EXtRestController.class);
                });
    }

    @Test
    public void givenPropertyRestApiEnable_whenTrue_thenGsonConfig() {
        applicationContextRunner
                .withPropertyValues(PropertyNameConsts.PROPERTY_APIJSON_RESTAPI_ENABLE + "=true")
                .withUserConfiguration(GsonApplicationConfiguration.GsonRestApiAutoConfiguration.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(GsonRestController.class);
                    assertThat(context).hasSingleBean(GsonEXtRestController.class);
                });
    }

    @Test
    public void givenPropertyRestApiEnable_whenFalse_thenGsonConfig() {
        applicationContextRunner
                .withPropertyValues(PropertyNameConsts.PROPERTY_APIJSON_RESTAPI_ENABLE + "=false")
                .withUserConfiguration(GsonApplicationConfiguration.GsonRestApiAutoConfiguration.class)
                .run(context -> {
                    assertThat(context).doesNotHaveBean(GsonRestController.class);
                    assertThat(context).doesNotHaveBean(GsonEXtRestController.class);
                });
    }
}
