package yunjiao.springboot.autoconfigure.apijson;

import apijson.framework.APIJSONFunctionParser;
import apijson.orm.AbstractFunctionParser;
import apijson.orm.script.ScriptExecutor;
import org.junit.jupiter.api.Test;
import yunjiao.springboot.extension.apijson.ApijsonFunctionParserConfigurer;

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
    void givenImplement_whenBuild_thenCheckStaticOk() {
        ApijsonFunctionParserConfigurer configurer = new ApijsonFunctionParserConfigurerDemo();
        ApijsonParserProperties.Function function = new ApijsonParserProperties.Function();
        function.setParseArgValue(true);
        function.setEnableRemoteFunction(false);
        function.setEnableScriptFunction(false);
        ApijsonUtils.forceInit(APIJSONFunctionParser.class);
        ApijsonUtils.buildAPIJSONFunctionParserStatic(function, List.of(configurer));

        assertThat(APIJSONFunctionParser.ENABLE_REMOTE_FUNCTION).isEqualTo(function.isEnableRemoteFunction());
        assertThat(APIJSONFunctionParser.ENABLE_SCRIPT_FUNCTION).isEqualTo(function.isEnableScriptFunction());
        assertThat(APIJSONFunctionParser.IS_PARSE_ARG_VALUE).isEqualTo(function.isParseArgValue());
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
