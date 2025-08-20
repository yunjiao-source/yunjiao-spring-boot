package io.yunjiao.springboot.autoconfigure.apijson;

import io.yunjiao.springboot.autoconfigure.util.PropertyNameConsts;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * APIJSON 配置属性
 *
 * @author yangyunjiao
 */
@Data
@ConfigurationProperties(prefix = PropertyNameConsts.PROPERTY_PREFIX_APIJSON)
public class ApijsonProperties {
    @NestedConfigurationProperty
    private RestApi restApi = new RestApi();

    /**
     * 请求需要校验，默认true，开发时应该设置成false
     */
    private boolean needVerifyLogin = true;

    /**
     * 请求需要校验，默认true，开发时应该设置成false
     */
    private boolean needVerifyRole = true;

    /**
     * 请求需要校验，默认true
     */
    private boolean needVerifyContent = true;

    /**
     * 启动时初始化， 默认false
     */
    private boolean enableOnStartup = false;

    /**
     * 服务异常时停止
     */
    private boolean shutdownWhenServerError = true;

    /**
     * apijson debug日志输出，默认false
     */
    private boolean logDebug = false;

    /**
     * 应用程序类型， 默认fastjson2
     */
    private Application application = Application.fastjson2;

    /**
     * 主键生成策略，默认timestamp
     */
    private NewIdStrategy newIdStrategy = NewIdStrategy.timestamp;

    /**
     * 应用程序
     */
    public enum Application {
        /**
         * fastjson2 json解析工具
         */
        fastjson2,

        /**
         * Gson json解析工具
         */
        gson
    }

    /**
     * 主键生成策略
     */
    public enum NewIdStrategy {
        /**
         * 数据库自增
         */
        database,

        /**
         * uuid字符串
         */
        uuid,

        /**
         * 当前时间毫秒数
         */
        timestamp,

        /**
         * 雪花算法
         */
        snowflake,

        /**
         * 用户自定义。需要实现 {@link NewIdStrategy} 接口并配置
         */
        custom
    }

    /**
     * APIJSON接口配置
     */
    @Data
    public static class RestApi {
        /**
         * 开启接口，默认true
         */
        private boolean enable = true;

        /**
         * 接口前缀，默认api-json
         */
        private String prefix = "api-json";
    }

}
