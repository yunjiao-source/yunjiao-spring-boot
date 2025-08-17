package io.yunjiao.spring.boot.autoconfigure.apijson;

import apijson.RequestMethod;
import apijson.framework.*;
import apijson.orm.AbstractFunctionParser;
import apijson.orm.AbstractSQLConfig;
import apijson.orm.AbstractVerifier;
import apijson.orm.Entry;
import apijson.orm.script.ScriptExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

/**
 * ApiJson 工具
 *
 * @author yangyunjiao
 */
@Slf4j
public final class ApijsonUtils {
    /**
     * 使用反射强制初始化
     *
     * @param clazz 类
     */
    public static void forceInit(Class<?> clazz) {
        try {
            Class.forName(clazz.getName(), true, clazz.getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取键key对应的值，循环校验每个对象
     * @param o 对象
     * @param checkFn 检查函数
     */
    public static void checkItemInList(Object o, BiConsumer<Object, Integer> checkFn) {
        if (o == null) {
            return;
        }

        if (!(o instanceof Collection<?>)) {
            throw new IllegalArgumentException(o + " 不符合 Array 数组类型! 结构必须是 [] ！");
        }

        int i = -1;
        Collection<?> collection = (Collection<?>)o;
        for (Object item : collection) {
            i++;
            checkFn.accept(item, i);
        }
    }


    /**
     * {@link APIJSONSQLConfig} 静态属性配置
     *
     * @param config                属性
     * @param configurers           配置器
     */
    public static void buildAPIJSONSQLConfigStatic(ApijsonSqlProperties.Config config, List<ApijsonSqlConfigConfigurer> configurers) {
        APIJSONSQLConfig.DEFAULT_DATABASE = config.getDefaultDatabase();
        APIJSONSQLConfig.DEFAULT_SCHEMA = config.getDefaultSchema();
        APIJSONSQLConfig.DEFAULT_CATALOG = config.getDefaultCatalog();
        APIJSONSQLConfig.DEFAULT_NAMESPACE = config.getDefaultNamespace();
        APIJSONSQLConfig.ENABLE_COLUMN_CONFIG = config.isEnableColumnConfig();

        Map<String, String> rawMap = new LinkedHashMap<>();
        Map<Integer, Map<String, List<String>>> versionedTableColumnMap = new HashMap<>();
        Map<Integer, Map<String, Map<String, String>>> versionedKeyColumnMap = new HashMap<>();

        for (ApijsonSqlConfigConfigurer configurer : configurers) {
            Map<String, String> rawMapTmp = new LinkedHashMap<>();
            Map<String, List<String>> versionedTableColumnMapTmp = new HashMap<>();
            Map<String, Map<String, String>> versionedKeyColumnMapTmp = new HashMap<>();

            int vtcmIndex = 0;
            int vkcmIndex = 0;
            configurer.configure(rawMapTmp, versionedTableColumnMapTmp, versionedKeyColumnMapTmp);
            if (!ObjectUtils.isEmpty(rawMapTmp)) {
                rawMap.putAll(rawMapTmp);
            }
            if (!ObjectUtils.isEmpty(versionedTableColumnMapTmp)) {
                versionedTableColumnMap.put(vtcmIndex++, versionedTableColumnMapTmp);
            }
            if (!ObjectUtils.isEmpty(versionedKeyColumnMapTmp)) {
                versionedKeyColumnMap.put(vkcmIndex++, versionedKeyColumnMapTmp);
            }

        }

        if (log.isDebugEnabled()) {
            log.debug("RAW_MAP={}", rawMap);
            log.debug("VERSIONED_TABLE_COLUMN_MAP={}", versionedTableColumnMap);
            log.debug("VERSIONED_KEY_COLUMN_MAP={}", versionedKeyColumnMap);
        }
        if (!ObjectUtils.isEmpty(rawMap)) {
            AbstractSQLConfig.RAW_MAP.putAll(rawMap);
        }
        if (!ObjectUtils.isEmpty(versionedTableColumnMap)) {
            ColumnUtil.VERSIONED_TABLE_COLUMN_MAP.putAll(versionedTableColumnMap);
        }
        if (!ObjectUtils.isEmpty(versionedKeyColumnMap)) {
            ColumnUtil.VERSIONED_KEY_COLUMN_MAP.putAll(versionedKeyColumnMap);
        }

    }

    /**
     * {@link APIJSONSQLExecutor} 静态属性配置
     * @param executor 属性
     */
    public static void buildAPIJSONSQLExecutorStatic(ApijsonSqlProperties.Executor executor) {
        APIJSONSQLExecutor.ENABLE_OUTPUT_NULL_COLUMN = executor.isEnableOutputNullColumn();
        APIJSONSQLExecutor.KEY_RAW_LIST = executor.getKeyRawList();
        APIJSONSQLExecutor.KEY_VICE_ITEM = executor.getKeyViceItem();
    }

    /**
     * {@link APIJSONVerifier} 静态属性配置
     * @param verifier              属性
     * @param configurers           配置器
     */
    public static void buildAPIJSONVerifierStatic(ApijsonVerifierProperties verifier, List<ApijsonVerifierConfigurer> configurers) {
        APIJSONVerifier.IS_UPDATE_MUST_HAVE_ID_CONDITION = verifier.isUpdateMustHaveIdCondition();
        APIJSONVerifier.ENABLE_VERIFY_ROLE = verifier.isEnableVerifyRole();
        APIJSONVerifier.ENABLE_VERIFY_CONTENT = verifier.isEnableVerifyContent();
        APIJSONVerifier.ENABLE_VERIFY_COLUMN = verifier.isEnableVerifyColumn();
        APIJSONVerifier.ENABLE_APIJSON_ROUTER = verifier.isEnableApijsonRouter();

        Map<String, Entry<String, Object>> roleMap = new LinkedHashMap<>();
        List<String> operationKeyList = new ArrayList<>();
        Map<String, Map<RequestMethod, String[]>> systemAccessMap = new HashMap<String, Map<RequestMethod, String[]>>();
        Map<String, Map<RequestMethod, String[]>> accessMap =  new HashMap<>();
        Map<String, Pattern> compileMap = new HashMap<>();
        Map<String, SortedMap<Integer, Map<String, Object>>> requestMap = new HashMap<>();

        for (ApijsonVerifierConfigurer configurer : configurers) {
            Map<String, Entry<String, Object>> roleMapTmp = new LinkedHashMap<>();
            List<String> operationKeyListTmp = new ArrayList<>();
            Map<String, Map<RequestMethod, String[]>> systemAccessMapTmp = new HashMap<String, Map<RequestMethod, String[]>>();
            Map<String, Map<RequestMethod, String[]>> accessMapTmp = new HashMap<>();
            Map<String, Pattern> compileMapTmp = new HashMap<>();
            Map<String, SortedMap<Integer, Map<String, Object>>> requestMapTmp = new HashMap<>();

            configurer.configure(roleMapTmp, operationKeyListTmp, systemAccessMapTmp, accessMapTmp, compileMapTmp, requestMapTmp);
            if (!ObjectUtils.isEmpty(roleMapTmp)) {
                roleMap.putAll(roleMapTmp);
            }
            if (!ObjectUtils.isEmpty(operationKeyListTmp)) {
                operationKeyList.addAll(operationKeyListTmp);
            }
            if (!ObjectUtils.isEmpty(systemAccessMapTmp)) {
                systemAccessMap.putAll(systemAccessMapTmp);
            }
            if (!ObjectUtils.isEmpty(accessMapTmp)) {
                accessMap.putAll(accessMapTmp);
            }
            if (!ObjectUtils.isEmpty(compileMapTmp)) {
                compileMap.putAll(compileMapTmp);
            }
            if (!ObjectUtils.isEmpty(requestMapTmp)) {
                requestMap.putAll(requestMapTmp);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("ROLE_MAP={}", roleMap);
            log.debug("OPERATION_KEY_LIST={}", operationKeyList);
            log.debug("SYSTEM_ACCESS_MAP={}", systemAccessMap);
            log.debug("ACCESS_MAP={}", accessMap);
            log.debug("COMPILE_MAP={}", compileMap);
            log.debug("REQUEST_MAP={}", requestMap);
        }

        if (!ObjectUtils.isEmpty(roleMap)) {
            AbstractVerifier.ROLE_MAP.putAll(roleMap);
        }
        if (!ObjectUtils.isEmpty(operationKeyList)) {
            AbstractVerifier.OPERATION_KEY_LIST.addAll(operationKeyList);
        }
        if (!ObjectUtils.isEmpty(systemAccessMap)) {
            AbstractVerifier.SYSTEM_ACCESS_MAP.putAll(systemAccessMap);
        }
        if (!ObjectUtils.isEmpty(accessMap)) {
            AbstractVerifier.ACCESS_MAP.putAll(accessMap);
        }
        if (!ObjectUtils.isEmpty(compileMap)) {
            AbstractVerifier.COMPILE_MAP.putAll(compileMap);
        }
        if (!ObjectUtils.isEmpty(requestMap)) {
            AbstractVerifier.REQUEST_MAP.putAll(requestMap);
        }
    }

    /**
     * {@link APIJSONParser} 静态属性配置
     *
     * @param request 属性
     */
    public static void buildAPIJSONParserStatic(ApijsonParserProperties.Request request) {
        APIJSONParser.IS_PRINT_REQUEST_STRING_LOG = request.isPrintRequestStringLog();
        APIJSONParser.IS_PRINT_BIG_LOG = request.isPrintBigLog();
        APIJSONParser.IS_PRINT_REQUEST_ENDTIME_LOG = request.isPrintRequestEndtimeLog();
        APIJSONParser.IS_RETURN_STACK_TRACE = request.isReturnStackTrace();
        APIJSONParser.IS_START_FROM_1 = request.isStartFrom1();
    }

    /**
     * {@link APIJSONObjectParser} 静态属性配置
     *
     * @param object 属性
     */
    public static void buildAPIJSONObjectParserStatic(ApijsonParserProperties.Object object) {
        //APIJSONObjectParser.
    }

    /**
     * {@link APIJSONFunctionParser} 静态属性配置
     *
     * @param function 远程函数属性
     * @param configurers 远程函数解析器配置
     */
    public static void buildAPIJSONFunctionParserStatic(ApijsonParserProperties.Function function,
                                                        List<ApijsonFunctionParserConfigurer> configurers) {
        APIJSONFunctionParser.ENABLE_REMOTE_FUNCTION = function.isEnableRemoteFunction();
        APIJSONFunctionParser.ENABLE_SCRIPT_FUNCTION = function.isEnableScriptFunction();
        APIJSONFunctionParser.IS_PARSE_ARG_VALUE = function.isParseArgValue();

        Map<String, ScriptExecutor<?, ? extends Map<String, Object>, ? extends List<Object>>> scriptExecutorMap = new HashMap<>();
        Map<String, Map<String, Object>> functionMap = new HashMap<>();

        for (ApijsonFunctionParserConfigurer configurer : configurers) {
            Map<String, ScriptExecutor<?, ? extends Map<String, Object>, ? extends List<Object>>> scriptExecutorMapTmp = new HashMap<>();
            Map<String, Map<String, Object>> functionMapTmp = new HashMap<>();

            configurer.configure(scriptExecutorMapTmp, functionMapTmp);
            if (!ObjectUtils.isEmpty(scriptExecutorMapTmp)) {
                scriptExecutorMap.putAll(scriptExecutorMapTmp);
            }
            if (!ObjectUtils.isEmpty(functionMapTmp)) {
                functionMap.putAll(functionMapTmp);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("SCRIPT_EXECUTOR_MAP={}", scriptExecutorMap);
            log.debug("FUNCTION_MAP={}", functionMap);
        }

        if (!ObjectUtils.isEmpty(scriptExecutorMap)) {
            AbstractFunctionParser.SCRIPT_EXECUTOR_MAP.putAll(scriptExecutorMap);
        }
        if (!ObjectUtils.isEmpty(functionMap)) {
            AbstractFunctionParser.FUNCTION_MAP.putAll(functionMap);
        }

    }

}
