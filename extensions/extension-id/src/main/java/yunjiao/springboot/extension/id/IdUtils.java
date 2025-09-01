package yunjiao.springboot.extension.id;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.extra.spring.SpringUtil;
import yunjiao.springboot.extension.id.uidgenerator.UidGeneratorCached;
import yunjiao.springboot.extension.id.uidgenerator.UidGeneratorDefault;

/**
 * ID 工具, 使用静态方法获取Spring容器中的ID生成器
 *
 * @author yangyunjiao
 */
public final class IdUtils {
    /**
     * 实例
     */
    private static Snowflake snowflake;

    /**
     * 实例
     */
    private static UidGeneratorCached uidGeneratorCached;

    /**
     * 实例
     */
    private static UidGeneratorDefault uidGeneratorDefault;

    /**
     * 从Spring容器中获取，并初始化实例，避免重复获取
     */
    private static synchronized void initSnowflake() {
        if (snowflake == null) {
            snowflake = SpringUtil.getBean(Snowflake.class);
        }
    }

    /**
     * 从Spring容器中获取，并初始化实例，避免重复获取
     */
    private static synchronized void initUidGeneratorCached() {
        if (uidGeneratorCached == null) {
            uidGeneratorCached = SpringUtil.getBean(UidGeneratorCached.class);
        }
    }

    /**
     * 从Spring容器中获取，并初始化实例，避免重复获取
     */
    private static synchronized void initUidGeneratorDefault() {
        if (uidGeneratorDefault == null) {
            uidGeneratorDefault = SpringUtil.getBean(UidGeneratorDefault.class);
        }
    }

    /**
     * 构造器
     */
    private IdUtils() {
    }

    /**
     * 获取
     *
     * @return 实例
     */
    public static Snowflake getSnowflake() {
        if (snowflake == null) {
            initSnowflake();
        }

        return snowflake;
    }

    /**
     * 获取
     *
     * @return 实例
     */
    public static UidGeneratorCached getUidGeneratorCached() {
        if (uidGeneratorCached == null) {
            initUidGeneratorCached();
        }

        return uidGeneratorCached;
    }

    /**
     * 获取
     *
     * @return 实例
     */
    public static UidGeneratorDefault getUidGeneratorDefault() {
        if (uidGeneratorDefault == null) {
            initUidGeneratorDefault();
        }

        return uidGeneratorDefault;
    }

}
