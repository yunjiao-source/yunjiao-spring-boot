package io.yunjiao.springboot.autoconfigure.apijson.gson;

import apijson.RequestMethod;
import apijson.gson.APIJSONObjectParser;
import apijson.gson.APIJSONParser;
import apijson.orm.SQLConfig;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 请求解析器 {@link APIJSONParser} 子类， 默认实现
 *
 * @author yangyunjiao
 */
public class GsonParser extends APIJSONParser<Serializable> {
    /**
     * 默认构造器
     */
    public GsonParser() {
    }

    public GsonParser(RequestMethod method) {
        super(method);
    }

    public GsonParser(RequestMethod method, boolean needVerify) {
        super(method, needVerify);
    }

    @Override
    public APIJSONObjectParser<Serializable> createObjectParser(Map<String, Object> request, String parentPath, SQLConfig<Serializable, Map<String, Object>, List<Object>> arrayConfig, boolean isSubquery, boolean isTable, boolean isArrayMainTable) throws Exception {
        return new GsonObjectParser(getSession(), request, parentPath, arrayConfig, isSubquery, isTable, isArrayMainTable).setMethod(getMethod()).setParser(this);
    }
}
