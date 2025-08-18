package io.yunjiao.springboot.autoconfigure.apijson;

import io.yunjiao.springboot.autoconfigure.util.PropertyNameConsts;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 解析器配置
 *
 * @author yangyunjiao
 */
@Data
@ConfigurationProperties(prefix = PropertyNameConsts.PROPERTY_PREFIX_APIJSON_PARSER)
public class ApijsonParserProperties {
    /**
     * 函数
     */
    @NestedConfigurationProperty
    private Function function = new Function();

    /**
     * 请求
     */
    @NestedConfigurationProperty
    private Request request = new Request();

    /**
     * 对象解析器
     */
    @NestedConfigurationProperty
    private Object object = new Object();

    /**
     * 函数配置。 参考 {@link apijson.framework.APIJSONFunctionParser}
     */
    @Data
    public static class Function {
        /**
         * 是否解析参数 key 的对应的值
         */
        private boolean isParseArgValue = false;

        /**
         * 开启支持远程函数
         */
        private boolean enableRemoteFunction = true;

        /**
         * 开启支持远程函数中的 JavaScript 脚本形式
         */
        private boolean enableScriptFunction = true;
    }

    /**
     * 请求配置。 参考 {@link apijson.framework.APIJSONParser}
     */
    @Data
    public static class Request {
        /**
         * 是否打印关键的接口请求内容
         */
        private boolean isPrintRequestStringLog = false;

        /**
         * 打印大数据量日志的标识
         */
        private boolean isPrintBigLog = false;

        /**
         * 是否打印关键的接口请求结束时间
         */
        private boolean isPrintRequestEndtimeLog = false;

        /**
         * 控制返回 trace:stack 字段
         */
        private boolean isReturnStackTrace = true;

        /**
         * 分页页码是否从 1 开始，默认为从 0 开始
         */
        private boolean isStartFrom1 = false;
    }

    /**
     * 对象配置。 参考 {@link apijson.framework.APIJSONObjectParser}
     */
    @Data
    public static class Object {

    }
}
