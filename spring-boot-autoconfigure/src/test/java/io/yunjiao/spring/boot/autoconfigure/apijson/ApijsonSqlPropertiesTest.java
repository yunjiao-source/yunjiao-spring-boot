package io.yunjiao.spring.boot.autoconfigure.apijson;

import io.yunjiao.spring.apijson.util.ApijsonConsts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link ApijsonSqlProperties} 单元测试用户
 *
 * @author yangyunjiao
 */
@SpringBootTest()
public class ApijsonSqlPropertiesTest {
    @Autowired
    private ApijsonSqlProperties properties;

    @Test
    public void configDefaultValue() {
        ApijsonSqlProperties.Config confg = properties.getConfig();
        assertThat(confg.getDefaultDatabase()).isEqualTo(ApijsonConsts.SQL_CONFIG_DEFAULT_DATABASE);
        assertThat(confg.getDefaultSchema()).isEqualTo(ApijsonConsts.SQL_CONFIG_DEFAULT_SCHEMA);

    }

    @Test
    public void excutorDefaultValue() {
        ApijsonSqlProperties.Executor executor = properties.getExecutor();
        assertThat(executor.getKeyRawList()).isEqualTo(ApijsonConsts.SQL_EXECUTOR_KEY_RAW_LIST);
        assertThat(executor.getKeyViceItem()).isEqualTo(ApijsonConsts.SQL_EXECUTOR_KEY_VICE_ITEM);

    }

    @SpringBootConfiguration
    @EnableConfigurationProperties(ApijsonSqlProperties.class)
    static class Config {
    }
}
