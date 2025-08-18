package io.yunjiao.springboot.autoconfigure.apijson.fastjson2;

import apijson.Log;
import apijson.fastjson2.*;
import io.yunjiao.springboot.autoconfigure.apijson.ApijsonProperties;
import io.yunjiao.springboot.autoconfigure.apijson.ApijsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;

/**
 * Fastjson2 应用初始化Bean
 *
 * @author yangyunjiao
 */
@RequiredArgsConstructor
public class Fastjson2InitializingBean implements InitializingBean {
    private final Fastjson2SimpleCallback fastjson2SimpleCallback;

    private final Fastjson2Creator fastjson2Creator;

    private final ApijsonProperties properties;

    @Override
    public void afterPropertiesSet() throws Exception {
        forceInitClass();
        initAPIJSONApplication();
    }

    /**
     * 强迫加载类，执行静态代码块
     */
    void forceInitClass() {
        ApijsonUtils.forceInit(APIJSONApplication.class);
        ApijsonUtils.forceInit(APIJSONController.class);
        ApijsonUtils.forceInit(APIJSONFunctionParser.class);
        ApijsonUtils.forceInit(APIJSONObjectParser.class);
        ApijsonUtils.forceInit(APIJSONParser.class);
        ApijsonUtils.forceInit(APIJSONSQLConfig.class);
        ApijsonUtils.forceInit(APIJSONSQLExecutor.class);
        ApijsonUtils.forceInit(APIJSONVerifier.class);
    }

    /**
     * {@link APIJSONApplication} 初始化
     *
     * @throws Exception 初始化化异常
     */
    void initAPIJSONApplication() throws Exception {
        Fastjson2SqlConfig.SIMPLE_CALLBACK = fastjson2SimpleCallback;
        Log.DEBUG = properties.isLogDebug();
        APIJSONApplication.init(properties.isShutdownWhenServerError(), fastjson2Creator);
        if (properties.isEnableOnStartup()) {
            Fastjson2Verifier.init(properties.isShutdownWhenServerError(), fastjson2Creator);
        }
    }
}
