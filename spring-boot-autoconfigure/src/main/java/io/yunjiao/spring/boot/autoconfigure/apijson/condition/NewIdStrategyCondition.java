package io.yunjiao.spring.boot.autoconfigure.apijson.condition;

import io.yunjiao.spring.boot.autoconfigure.apijson.ApijsonProperties;
import io.yunjiao.spring.boot.autoconfigure.condition.EnumPropertyCondition;
import io.yunjiao.spring.boot.autoconfigure.util.PropertyNameConsts;

/**
 * 主键生成策略条件
 *
 * @author yangyunjiao
 */
public class NewIdStrategyCondition extends EnumPropertyCondition<ApijsonProperties.NewIdStrategy> {
    protected NewIdStrategyCondition(ApijsonProperties.NewIdStrategy expectedValue) {
        super(expectedValue, ApijsonProperties.NewIdStrategy.class, PropertyNameConsts.PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY);
    }

    public static class OnDatabase extends NewIdStrategyCondition {
        public OnDatabase() {
            super(ApijsonProperties.NewIdStrategy.database);
        }
    }

    public static class OnUuid extends NewIdStrategyCondition {
        public OnUuid() {
            super(ApijsonProperties.NewIdStrategy.uuid);
        }
    }

    public static class OnTimestamp extends NewIdStrategyCondition {
        public OnTimestamp() {
            super(ApijsonProperties.NewIdStrategy.timestamp);
        }
    }

    public static class OnSnowflake extends NewIdStrategyCondition {
        public OnSnowflake() {
            super(ApijsonProperties.NewIdStrategy.snowflake);
        }
    }

    public static class OnCustom extends NewIdStrategyCondition {
        public OnCustom() {
            super(ApijsonProperties.NewIdStrategy.custom);
        }
    }
}
