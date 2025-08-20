package io.yunjiao.springboot.autoconfigure.apijson.gson;

import apijson.gson.*;
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
public class GsonInitializingBean  implements InitializingBean {
    private final GsonSimpleCallback gsonSimpleCallback;

    private final GsonCreator gsonCreator;

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

    void initAPIJSONApplication() throws Exception {
        GsonSqlConfig.SIMPLE_CALLBACK = gsonSimpleCallback;
        APIJSONApplication.init(properties.isShutdownWhenServerError(), gsonCreator);
        if (properties.isEnableOnStartup()) {
            GsonVerifier.init(false, gsonCreator);
        }
    }
}
