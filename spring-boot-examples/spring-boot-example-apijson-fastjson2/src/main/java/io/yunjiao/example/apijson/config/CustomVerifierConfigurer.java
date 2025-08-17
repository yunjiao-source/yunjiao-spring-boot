package io.yunjiao.example.apijson.config;

import apijson.RequestMethod;
import apijson.StringUtil;
import apijson.orm.Entry;
import io.yunjiao.spring.boot.autoconfigure.apijson.ApijsonVerifierConfigurer;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.regex.Pattern;

/**
 * 校验器配置
 *
 * @author yangyunjiao
 */
@Configuration
public class CustomVerifierConfigurer implements ApijsonVerifierConfigurer {
    @Override
    public void configure(Map<String, Entry<String, Object>> roleMap, List<String> operationKeyList, Map<String, Map<RequestMethod, String[]>> systemAccessMap, Map<String, Map<RequestMethod, String[]>> accessNap, Map<String, Pattern> compileMap, Map<String, SortedMap<Integer, Map<String, Object>>> requestMap) {
        compileMap.put("PHONE", StringUtil.PATTERN_PHONE);
        compileMap.put("EMAIL", StringUtil.PATTERN_EMAIL);
        compileMap.put("ID_CARD", StringUtil.PATTERN_ID_CARD);
    }
}
