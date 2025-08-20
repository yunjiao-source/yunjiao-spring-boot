package io.yunjiao.springboot.autoconfigure.apijson.gson;

import apijson.RequestMethod;
import apijson.gson.APIJSONObjectParser;
import apijson.orm.Join;
import apijson.orm.SQLConfig;
import jakarta.servlet.http.HttpSession;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 对象解析器{@link APIJSONObjectParser} 子类, 默认实现
 *
 * @author yangyunjiao
 */
public class GsonObjectParser extends APIJSONObjectParser<Serializable> {
    /**
     * 标识
     */
    public static final  String TAG = GsonObjectParser.class.getSimpleName();

    /**
     * 构造器，直接调用父类
     *
     * @param session 必须值
     * @param request 必须值
     * @param parentPath 必须值
     * @param arrayConfig 必须值
     * @param isSubquery 必须值
     * @param isTable 必须值
     * @param isArrayMainTable 必须值
     * @throws Exception 失败
     */
    public GsonObjectParser(HttpSession session, Map<String, Object> request, String parentPath, SQLConfig<Serializable, Map<String, Object>, List<Object>> arrayConfig, boolean isSubquery, boolean isTable, boolean isArrayMainTable) throws Exception {
        super(session, request, parentPath, arrayConfig, isSubquery, isTable, isArrayMainTable);
    }

    @Override
    public SQLConfig<Serializable, Map<String, Object>, List<Object>> newSQLConfig(RequestMethod method, String table, String alias, Map<String, Object> request, List<Join<Serializable, Map<String, Object>, List<Object>>> joins, boolean isProcedure) throws Exception {
        return GsonSqlConfig.newSQLConfig2(method, table, alias, request, joins, isProcedure);
    }

}
