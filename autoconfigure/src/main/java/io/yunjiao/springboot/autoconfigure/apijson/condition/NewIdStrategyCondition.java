package io.yunjiao.springboot.autoconfigure.apijson.condition;

import io.yunjiao.springboot.autoconfigure.apijson.ApijsonProperties;
import io.yunjiao.springboot.autoconfigure.condition.EnumPropertyCondition;
import io.yunjiao.springboot.autoconfigure.util.PropertyNameConsts;

/**
 * 主键生成策略条件
 *
 * @author yangyunjiao
 */
public class NewIdStrategyCondition extends EnumPropertyCondition<ApijsonProperties.NewIdStrategy> {
    /**
     * 构造器
     *
     * @param expectedValue 必须值
     */
    protected NewIdStrategyCondition(ApijsonProperties.NewIdStrategy expectedValue) {
        super(expectedValue, ApijsonProperties.NewIdStrategy.class, PropertyNameConsts.PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY);
    }

    /**
     * 数据库生成方式
     */
    public static class OnDatabase extends NewIdStrategyCondition {
        /**
         * 构造器
         */
        public OnDatabase() {
            super(ApijsonProperties.NewIdStrategy.database);
        }
    }

    /**
     * uuid方式
     */
    public static class OnUuid extends NewIdStrategyCondition {
        /**
         * 构造器
         */
        public OnUuid() {
            super(ApijsonProperties.NewIdStrategy.uuid);
        }
    }

    /**
     * 时间戳方式
     */
    public static class OnTimestamp extends NewIdStrategyCondition {
        /**
         * 构造器
         */
        public OnTimestamp() {
            super(ApijsonProperties.NewIdStrategy.timestamp);
        }
    }

    /**
     * 雪花模型方式
     */
    public static class OnSnowflake extends NewIdStrategyCondition {
        /**
         * 构造器
         */
        public OnSnowflake() {
            super(ApijsonProperties.NewIdStrategy.snowflake);
        }
    }

    /**
     * 用户自定义方式
     */
    public static class OnCustom extends NewIdStrategyCondition {
        /**
         * 构造器
         */
        public OnCustom() {
            super(ApijsonProperties.NewIdStrategy.custom);
        }
    }
}
