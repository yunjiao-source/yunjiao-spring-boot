package io.yunjiao.springboot.autoconfigure.apijson;

import apijson.orm.AbstractFunctionParser;
import apijson.orm.script.ScriptExecutor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link ApijsonFunctionParserConfigurer} 单元测试用例
 *
 * @author yangyunjiao
 */
public class ApijsonFunctionParserConfigurerTest {

    @Test
    void testDefault() {
        ApijsonFunctionParserConfigurer configurer = new ApijsonFunctionParserConfigurerDemo();
        ApijsonUtils.buildAPIJSONFunctionParserStatic(new ApijsonParserProperties.Function(), List.of(configurer));

        assertThat(AbstractFunctionParser.SCRIPT_EXECUTOR_MAP).hasSize(1);
        assertThat(AbstractFunctionParser.SCRIPT_EXECUTOR_MAP).containsKey("scriptExecutorMap");
        assertThat(AbstractFunctionParser.FUNCTION_MAP).hasSize(1);
        assertThat(AbstractFunctionParser.FUNCTION_MAP).containsKey("functionMap");
    }

    static class ApijsonFunctionParserConfigurerDemo implements ApijsonFunctionParserConfigurer {


        @Override
        public void configure(Map<String, ScriptExecutor<?, ? extends Map<String, Object>, ? extends List<Object>>> scriptExecutorMap,
                              Map<String, Map<String, Object>> functionMap) {
            scriptExecutorMap.put("scriptExecutorMap", null);
            functionMap.put("functionMap", Map.of("functionMap1", "functionMap2"));
        }
    }
}
