package io.yunjiao.springboot.autoconfigure.apijson;

import apijson.orm.script.ScriptExecutor;

import java.util.List;
import java.util.Map;

/**
 * AbstractVerifier 用户自定义配置
 *
 * @author yangyunjiao
 */
@FunctionalInterface
public interface ApijsonFunctionParserConfigurer {

    /**
     * 配置
     *
     * @param scriptExecutorMap 脚本执行器Map
     * @param functionMap 远程函数Map
     */
    void configure(Map<String, ScriptExecutor<?, ? extends Map<String, Object>, ? extends List<Object>>> scriptExecutorMap,
                   Map<String, Map<String, Object>> functionMap);
}
