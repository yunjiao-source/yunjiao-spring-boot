package io.yunjiao.springboot.autoconfigure.apijson;

import apijson.framework.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;

import java.util.stream.Collectors;

/**
 * APIJSON初始化Bean, 主要初始化静态属性
 *
 * @author yangyunjiao
 */
@RequiredArgsConstructor
public class ApijsonInitializingBean implements InitializingBean {
    /**
     * 解析器配置
     */
    private final ApijsonParserProperties parserProperties;

    /**
     * 校验器配置
     */
    private final ApijsonVerifierProperties verifierProperties;

    /**
     * sql配置
     */
    private final ApijsonSqlProperties sqlProperties;

    /**
     * sql配置器用户配置
     */
    private final ObjectProvider<ApijsonSqlConfigConfigurer> sqlConfigConfigurers;

    /**
     * 校验器用户配置
     */
    private final ObjectProvider<ApijsonVerifierConfigurer> apijsonVerifierConfigurers;

    /**
     * 远程函数解析器用户配置
     */
    private final ObjectProvider<ApijsonFunctionParserConfigurer> apijsonFunctionParserConfigurers;

    @Override
    public void afterPropertiesSet() throws Exception {
        forceInitClass();
        initSqlConfig();
        initSqlExecutor();
        initVerifier();
        initRequestParaser();
        initObjectParser();
        initFunctionParser();
    }

    /**
     * 强迫加载类，执行静态(static {})代码块逻辑
     */
    void forceInitClass() {
        ApijsonUtils.forceInit(APIJSONApplication.class);
        ApijsonUtils.forceInit(APIJSONConstant.class);
        ApijsonUtils.forceInit(APIJSONController.class);
        ApijsonUtils.forceInit(APIJSONFunctionParser.class);
        ApijsonUtils.forceInit(APIJSONObjectParser.class);
        ApijsonUtils.forceInit(APIJSONParser.class);
        ApijsonUtils.forceInit(APIJSONSQLConfig.class);
        ApijsonUtils.forceInit(APIJSONSQLExecutor.class);
        ApijsonUtils.forceInit(APIJSONVerifier.class);
        ApijsonUtils.forceInit(ColumnUtil.class);
    }

    /**
     * 初始化sql配置器静态属性
     */
    void initSqlConfig() {
        ApijsonSqlProperties.Config config = sqlProperties.getConfig();
        ApijsonUtils.buildAPIJSONSQLConfigStatic(config, sqlConfigConfigurers.orderedStream().collect(Collectors.toList()));
        ColumnUtil.init();
    }

    /**
     * 初始化sql执行器静态属性
     */
    void initSqlExecutor() {
        ApijsonSqlProperties.Executor executor = sqlProperties.getExecutor();
        ApijsonUtils.buildAPIJSONSQLExecutorStatic(executor);
    }

    /**
     * 初始化校验器器静态属性
     */
    void initVerifier() {
        ApijsonUtils.buildAPIJSONVerifierStatic(verifierProperties, apijsonVerifierConfigurers.orderedStream().collect(Collectors.toList()));
    }

    /**
     * 初始化请求解析器静态属性
     */
    void initRequestParaser() {
        ApijsonParserProperties.Request request = parserProperties.getRequest();
        ApijsonUtils.buildAPIJSONParserStatic(request);
    }

    /**
     * 初始化对象解析器静态属性
     */
    void initObjectParser() {
        ApijsonParserProperties.Object object = parserProperties.getObject();
        ApijsonUtils.buildAPIJSONObjectParserStatic(object);
    }

    /**
     *
     */
    void initFunctionParser() {
        ApijsonParserProperties.Function function = parserProperties.getFunction();
        ApijsonUtils.buildAPIJSONFunctionParserStatic(function, apijsonFunctionParserConfigurers.orderedStream().collect(Collectors.toList()));
    }
}
