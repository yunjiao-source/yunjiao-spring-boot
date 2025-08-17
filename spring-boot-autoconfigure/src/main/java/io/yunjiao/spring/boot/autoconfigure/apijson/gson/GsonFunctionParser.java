package io.yunjiao.spring.boot.autoconfigure.apijson.gson;


import apijson.gson.APIJSONFunctionParser;

import java.io.Serializable;

/**
 * 可远程调用的函数类 {@link APIJSONFunctionParser} 子类， 默认实现
 *
 * @author yangyunjiao
 */
public class GsonFunctionParser extends APIJSONFunctionParser<Serializable> {
    /**
     * 标识
     */
    public static final  String TAG = GsonFunctionParser.class.getSimpleName();



}
