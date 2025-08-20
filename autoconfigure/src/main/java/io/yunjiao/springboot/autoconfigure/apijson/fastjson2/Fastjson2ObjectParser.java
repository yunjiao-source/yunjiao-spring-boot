package io.yunjiao.springboot.autoconfigure.apijson.fastjson2;

import apijson.RequestMethod;
import apijson.fastjson2.APIJSONObjectParser;
import apijson.orm.Join;
import apijson.orm.SQLConfig;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpSession;

import java.io.Serializable;
import java.util.List;

/**
 * 对象解析器{@link APIJSONObjectParser} 子类, 默认实现
 *
 * @author yangyunjiao
 */
public class Fastjson2ObjectParser  extends APIJSONObjectParser<Serializable> {
    /**
     * 标识
     */
    public static final  String TAG = Fastjson2ObjectParser.class.getSimpleName();

    /**
     * 构造对象
     *
     * @param session 会话
     * @param request 请求
     * @param parentPath 父离家
     * @param arrayConfig 配置
     * @param isSubquery 是否子查询
     * @param isTable 是否表
     * @param isArrayMainTable 是否主表
     * @throws Exception 异常
     */
    public Fastjson2ObjectParser(HttpSession session, JSONObject request, String parentPath, SQLConfig<Serializable, JSONObject, JSONArray> arrayConfig, boolean isSubquery, boolean isTable, boolean isArrayMainTable) throws Exception {
        super(session, request, parentPath, arrayConfig, isSubquery, isTable, isArrayMainTable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SQLConfig<Serializable, JSONObject, JSONArray> newSQLConfig(RequestMethod method, String table, String alias, JSONObject request, List<Join<Serializable, JSONObject, JSONArray>> joins, boolean isProcedure) throws Exception {
        return Fastjson2SqlConfig.newSQLConfig(method, table, alias, request, joinList, isProcedure);
    }
}
