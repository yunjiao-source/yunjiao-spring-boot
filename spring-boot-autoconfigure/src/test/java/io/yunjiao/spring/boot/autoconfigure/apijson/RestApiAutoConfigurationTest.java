package io.yunjiao.spring.boot.autoconfigure.apijson;

import io.yunjiao.spring.boot.autoconfigure.apijson.fastjson2.Fastjson2EXtRestController;
import io.yunjiao.spring.boot.autoconfigure.apijson.fastjson2.Fastjson2RestController;
import io.yunjiao.spring.boot.autoconfigure.apijson.gson.GsonEXtRestController;
import io.yunjiao.spring.boot.autoconfigure.apijson.gson.GsonRestController;
import io.yunjiao.spring.boot.autoconfigure.util.PropertyNameConsts;
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
    public void fastjson2RestApiEnabled() {
        applicationContextRunner
                .withPropertyValues(PropertyNameConsts.PROPERTY_PREFIX_APIJSON_RESTAPI_ENABLE + "=true")
                .withUserConfiguration(Fastjson2ApplicationAutoConfiguration.Fastjson2RestApiAutoConfiguration.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(Fastjson2RestController.class);
                    assertThat(context).hasSingleBean(Fastjson2EXtRestController.class);
                });
    }

    @Test
    public void fastjson2RestApiDisabled() {
        applicationContextRunner
                .withPropertyValues(PropertyNameConsts.PROPERTY_PREFIX_APIJSON_RESTAPI_ENABLE + "=false")
                .withUserConfiguration(Fastjson2ApplicationAutoConfiguration.Fastjson2RestApiAutoConfiguration.class)
                .run(context -> {
                    assertThat(context).doesNotHaveBean(Fastjson2RestController.class);
                    assertThat(context).doesNotHaveBean(Fastjson2EXtRestController.class);
                });
    }

    @Test
    public void GsonRestApiEnabled() {
        applicationContextRunner
                .withPropertyValues(PropertyNameConsts.PROPERTY_PREFIX_APIJSON_RESTAPI_ENABLE + "=true")
                .withUserConfiguration(GsonApplicationAutoConfiguration.GsonRestApiAutoConfiguration.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(GsonRestController.class);
                    assertThat(context).hasSingleBean(GsonEXtRestController.class);
                });
    }

    @Test
    public void GsonRestApiDisabled() {
        applicationContextRunner
                .withPropertyValues(PropertyNameConsts.PROPERTY_PREFIX_APIJSON_RESTAPI_ENABLE + "=false")
                .withUserConfiguration(GsonApplicationAutoConfiguration.GsonRestApiAutoConfiguration.class)
                .run(context -> {
                    assertThat(context).doesNotHaveBean(GsonRestController.class);
                    assertThat(context).doesNotHaveBean(GsonEXtRestController.class);
                });
    }
}
