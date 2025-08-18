package io.yunjiao.springboot.autoconfigure.apijson.condition;


import io.yunjiao.springboot.autoconfigure.apijson.ApijsonProperties;
import io.yunjiao.springboot.autoconfigure.condition.EnumPropertyCondition;
import io.yunjiao.springboot.autoconfigure.util.PropertyNameConsts;

/**
 * 应用程序类型条件
 *
 * @author yangyunjiao
 */
public class ApllicationCondition extends EnumPropertyCondition<ApijsonProperties.Application> {
    /**
     * 构造器
     * @param expectedValue 希望值
     */
    protected ApllicationCondition(ApijsonProperties.Application expectedValue) {
        super(expectedValue, ApijsonProperties.Application.class, PropertyNameConsts.PROPERTY_PREFIX_APIJSON_APPLICATION);
    }

    /**
     * fastjson2条件
     */
    public static class OnFastjson2 extends ApllicationCondition {
        /**
         * 默认构造器
         */
        public OnFastjson2() {
            super(ApijsonProperties.Application.fastjson2);
        }
    }

    /**
     * Gson条件
     */
    public static class OnGson extends ApllicationCondition {
        /**
         * 默认构造器
         */
        public OnGson() {
            super(ApijsonProperties.Application.gson);
        }
    }
}
