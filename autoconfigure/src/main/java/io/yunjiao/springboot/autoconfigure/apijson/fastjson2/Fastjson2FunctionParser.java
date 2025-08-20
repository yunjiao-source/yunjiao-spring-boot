package io.yunjiao.springboot.autoconfigure.apijson.fastjson2;


import apijson.fastjson2.APIJSONFunctionParser;

import java.io.Serializable;

/**
 * 可远程调用的函数类 {@link APIJSONFunctionParser} 子类， 默认实现
 *
 * @author yangyunjiao
 */

public class Fastjson2FunctionParser extends APIJSONFunctionParser<Serializable> {
    /**
     * 标识
     */
    public static final String TAG = Fastjson2FunctionParser.class.getSimpleName();
}
