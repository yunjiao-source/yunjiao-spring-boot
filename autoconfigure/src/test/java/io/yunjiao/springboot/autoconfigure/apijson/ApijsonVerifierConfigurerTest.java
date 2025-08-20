package io.yunjiao.springboot.autoconfigure.apijson;

import apijson.RequestMethod;
import apijson.orm.AbstractVerifier;
import apijson.orm.Entry;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link ApijsonVerifierConfigurer} 单元测试用例
 *
 * @author yangyunjiao
 */
public class ApijsonVerifierConfigurerTest {

    @Test
    void testDefault() {
        ApijsonVerifierConfigurerDemo configurer = new ApijsonVerifierConfigurerDemo();
        ApijsonUtils.buildAPIJSONVerifierStatic(new ApijsonVerifierProperties(), List.of(configurer));

        assertThat(AbstractVerifier.ROLE_MAP).containsKey("roleMap");
        assertThat(AbstractVerifier.OPERATION_KEY_LIST).contains("operationKeyList");
        assertThat(AbstractVerifier.SYSTEM_ACCESS_MAP).containsKey("systemAccessMap");
        assertThat(AbstractVerifier.ACCESS_MAP).containsKey("accessNap");

        assertThat(AbstractVerifier.COMPILE_MAP).hasSize(1);
        assertThat(AbstractVerifier.COMPILE_MAP).containsKey("compileMap");
        assertThat(AbstractVerifier.REQUEST_MAP).hasSize(1);
        assertThat(AbstractVerifier.REQUEST_MAP).containsKey("requestMap");
    }

    static class ApijsonVerifierConfigurerDemo implements ApijsonVerifierConfigurer {

        @Override
        public void configure(Map<String, Entry<String, Object>> roleMap,
                              List<String> operationKeyList,
                              Map<String, Map<RequestMethod, String[]>> systemAccessMap,
                              Map<String, Map<RequestMethod, String[]>> accessNap,
                              Map<String, Pattern> compileMap,
                              Map<String, SortedMap<Integer, Map<String, Object>>> requestMap) {
            roleMap.put("roleMap", new Entry<>("roleMap1", "roleMap2"));
            operationKeyList.add("operationKeyList");
            systemAccessMap.put("systemAccessMap", null);
            accessNap.put("accessNap", null);
            compileMap.put("compileMap", null);
            requestMap.put("requestMap", null);
        }
    }
}
