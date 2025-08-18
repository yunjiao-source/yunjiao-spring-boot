package io.yunjiao.springboot.autoconfigure.apijson.fastjson2;

import apijson.RequestMethod;
import apijson.fastjson2.APIJSONObjectParser;
import apijson.fastjson2.APIJSONParser;
import apijson.orm.SQLConfig;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.io.Serializable;

/**
 * 请求解析器 {@link APIJSONParser} 子类， 默认实现
 *
 * @author yangyunjiao
 */
public class Fastjson2Parser extends APIJSONParser<Serializable> {
    /**
     * 默认构造器
     */
    public Fastjson2Parser() {
    }

    public Fastjson2Parser(RequestMethod method) {
        super(method);
    }

    public Fastjson2Parser(RequestMethod method, boolean needVerify) {
        super(method, needVerify);
    }

    @Override
    public APIJSONObjectParser<Serializable> createObjectParser(JSONObject request, String parentPath, SQLConfig<Serializable, JSONObject, JSONArray> arrayConfig, boolean isSubquery, boolean isTable, boolean isArrayMainTable) throws Exception {
        return new Fastjson2ObjectParser(getSession(), request, parentPath, arrayConfig, isSubquery, isTable, isArrayMainTable).setMethod(getMethod()).setParser(this);
    }

    @Override
    public JSONObject parseResponse(JSONObject request) {
        return super.parseResponse(request);
    }


}
