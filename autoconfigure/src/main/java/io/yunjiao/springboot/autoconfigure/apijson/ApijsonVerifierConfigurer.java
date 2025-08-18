package io.yunjiao.springboot.autoconfigure.apijson;

import apijson.RequestMethod;
import apijson.orm.Entry;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.regex.Pattern;

/**
 * AbstractVerifier 用户自定义配置
 *
 * @author yangyunjiao
 */
@FunctionalInterface
public interface ApijsonVerifierConfigurer {

    /**
     * 配置
     *
     * @param roleMap {@link apijson.orm.AbstractVerifier#ROLE_MAP} 配置
     * @param operationKeyList {@link apijson.orm.AbstractVerifier#OPERATION_KEY_LIST} 配置
     * @param systemAccessMap {@link apijson.orm.AbstractVerifier#SYSTEM_ACCESS_MAP} 配置
     * @param accessNap {@link apijson.orm.AbstractVerifier#ACCESS_MAP} 配置
     * @param compileMap {@link apijson.orm.AbstractVerifier#COMPILE_MAP} 配置
     * @param requestMap {@link apijson.orm.AbstractVerifier#REQUEST_MAP} 配置
     */
    void configure(Map<String, Entry<String, Object>> roleMap,
                   List<String> operationKeyList,
                   Map<String, Map<RequestMethod, String[]>> systemAccessMap,
                   Map<String, Map<RequestMethod, String[]>> accessNap,
                   Map<String, Pattern> compileMap,
                   Map<String, SortedMap<Integer, Map<String, Object>>> requestMap);
}
