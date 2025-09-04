package yunjiao.springboot.extension.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 枚举缓存, 单例模式
 *
 * @author yangyunjiao
 */
@Slf4j
public final class EnumCache {
    private EnumCache() {
    }

    /**
     * 获取实例，单例模式
     *
     * @return 实例
     */
    public static EnumCache getInstance() {
        return EnumCacheHolder.instance;
    }

    /**
     * 使用枚举值定位枚举
     * @param clazz 枚举类
     * @param lookupValue 定位值
     * @return 枚举实例
     * @param <E> 枚举类型
     */
    public <E extends Enum<?>> E lookupByName(Class<E> clazz, String lookupValue) {
        return lookupByValue(clazz, lookupValue, Enum::name, null);
    }

    /**
     * 使用枚举值定位枚举。未找到则返回默认值
     *
     * @param clazz 枚举类
     * @param lookupValue 定位值
     * @param defaultValue 默认值
     * @return 枚举实例
     * @param <E> 枚举类型
     */
    public <E extends Enum<?>> E lookupByName(Class<E> clazz, String lookupValue, E defaultValue) {
        return lookupByValue(clazz, lookupValue, Enum::name, defaultValue);
    }

    /**
     * 使用值定位枚举
     *
     * @param clazz 枚举类
     * @param lookupValue 定位值
     * @param getValue 获取枚举值方法引用
     * @return 枚举实例
     * @param <E> 枚举类型
     */
    public <E extends Enum<?>> E lookupByValue(Class<E> clazz, Object lookupValue, Function<E, Object> getValue) {
        return lookupByValue(clazz, lookupValue, getValue, null);
    }

    /**
     * 使用枚举值定位枚举。未找到则返回默认值
     *
     * @param clazz 枚举类
     * @param lookupValue 定位值
     * @param getValue 获取枚举值方法引用
     * @param defaultValue 默认值
     * @return 枚举实例
     * @param <E> 枚举类型
     */
    @SuppressWarnings({"unchecked"})
    public  <E extends Enum<?>> E lookupByValue(Class<E> clazz, Object lookupValue, Function<E, Object> getValue, E defaultValue) {
        assert clazz != null;
        assert getValue != null;

        if (!EnumCacheHolder.existCache(clazz)) {
            EnumCacheHolder.initCache(clazz);
        }

        List<Enum<?>> values = EnumCacheHolder.getValue(clazz).orElse(Collections.emptyList());
        for (Enum<?> value : values) {
            Object o = getValue.apply((E)value);
            if (Objects.equals(lookupValue, o)) {
                return (E)value;
            }
        }

        return defaultValue;
    }

    /**
     * Holder 类，包含所有静态属性
     */
    protected static final class EnumCacheHolder {
        /**
         * {@link EnumCache}自身实例
         */
        private static final EnumCache instance = new EnumCache();

        /**
         * 枚举值缓存
         */
        private static final Map<Class<? extends Enum<?>>, List<Enum<?>>> CHACHE = new ConcurrentHashMap<>();

        /**
         * 获取缓存值
         *
         * @param clazz 枚举类，键值
         * @return 枚举缓存值
         */
        public static Optional<List<Enum<?>>> getValue(Class<?> clazz) {
            return Optional.ofNullable(CHACHE.get(clazz));
        }

        /**
         * 枚举是否已经缓存
         *
         * @param clazz 枚举类，键值
         * @return 已缓存返回True；否则False
         */
        public static boolean existCache(Class<?> clazz) {
            return CHACHE.containsKey(clazz);
        }

        /**
         * 初始化枚举缓存。key=枚举类；value=枚举值
         * @param clazz 枚举类
         */
        public static synchronized void initCache(Class<? extends Enum<?>> clazz) {
            if (EnumCacheHolder.existCache(clazz)) {
                return;
            }

            final Enum<?>[] enums = clazz.getEnumConstants();
            CHACHE.put(clazz, List.of(enums));

            if (log.isDebugEnabled()) {
                log.debug("初始化枚举缓存: {}, 总共枚举缓存 {} 个", clazz.getName(), CHACHE.size());
            }

        }
    }
}
