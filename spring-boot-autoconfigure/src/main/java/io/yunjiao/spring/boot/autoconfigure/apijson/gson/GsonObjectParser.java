package io.yunjiao.spring.boot.autoconfigure.apijson.gson;

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

    public GsonObjectParser(HttpSession session, Map<String, Object> request, String parentPath, SQLConfig<Serializable, Map<String, Object>, List<Object>> arrayConfig, boolean isSubquery, boolean isTable, boolean isArrayMainTable) throws Exception {
        super(session, request, parentPath, arrayConfig, isSubquery, isTable, isArrayMainTable);
    }

    @Override
    public SQLConfig<Serializable, Map<String, Object>, List<Object>> newSQLConfig(RequestMethod method, String table, String alias, Map<String, Object> request, List<Join<Serializable, Map<String, Object>, List<Object>>> joins, boolean isProcedure) throws Exception {
        return GsonSqlConfig.newSQLConfig2(method, table, alias, request, joins, isProcedure);
    }

}
