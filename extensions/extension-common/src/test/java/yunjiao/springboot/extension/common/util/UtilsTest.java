package yunjiao.springboot.extension.common.util;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link Utils} 测试用例
 *
 * @author yangyunjiao
 */
public class UtilsTest {

    @Test
    void whenConvertEnv() {
        String NAME = "server.port";
        MockEnvironment env = new MockEnvironment();
        env.setProperty(NAME, "8080");

        // 名称存在
        Integer value = Utils.convertEnv(env, NAME, Integer.class, 0);
        assertThat(value).isEqualTo(8080);

        // 名称不存在存在
        value = Utils.convertEnv(env, "123", Integer.class, 0);
        assertThat(value).isEqualTo(0);
    }
}
