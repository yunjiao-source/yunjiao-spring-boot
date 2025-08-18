package io.yunjiao.springboot.autoconfigure.apijson;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link ApijsonProperties} 单元测试用户
 *
 * @author yangyunjiao
 */
@SpringBootTest()
public class ApijsonPropertiesTest {
    @Autowired
    private ApijsonProperties properties;

    @Test
    public void defaultValue() {
        assertThat(properties.getApplication()).isEqualTo(ApijsonProperties.Application.fastjson2);
        assertThat(properties.getNewIdStrategy()).isEqualTo(ApijsonProperties.NewIdStrategy.timestamp);
    }


    @SpringBootConfiguration
    @EnableConfigurationProperties(ApijsonProperties.class)
    static class Config {
    }
}
