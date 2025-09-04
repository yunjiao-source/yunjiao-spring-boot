package yunjiao.springboot.extension.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 枚举缓存
 *
 * @author yangyunjiao
 */
@Slf4j
public class EnumCache {
    private EnumCache() {
    }

    protected static final class EnumCacheHolder {
        private static final EnumCache instance = new EnumCache();
        private static final Map<Class<? extends Enum<?>>, List<Enum<?>>> CHACHE = new ConcurrentHashMap<>();

        public static Optional<List<Enum<?>>> getCache(Class<?> clazz) {
            return Optional.ofNullable(CHACHE.get(clazz));
        }

        public static boolean existCache(Class<?> clazz) {
            return CHACHE.containsKey(clazz);
        }

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

    public static EnumCache getInstance() {
        return EnumCacheHolder.instance;
    }

    public <E extends Enum<?>> E lookupByValue(Class<E> clazz, Object lookupValue, Function<E, Object> getValue) {
        return lookupByValue(clazz, lookupValue, getValue, null);
    }

    public <E extends Enum<?>> E lookupByName(Class<E> clazz, Object lookupValue) {
        return lookupByValue(clazz, lookupValue, Enum::name, null);
    }

    public <E extends Enum<?>> E lookupByName(Class<E> clazz, Object lookupValue, E defaultValue) {
        return lookupByValue(clazz, lookupValue, Enum::name, defaultValue);
    }

    @SuppressWarnings({"unchecked"})
    public  <E extends Enum<?>> E lookupByValue(Class<E> clazz, Object lookupValue, Function<E, Object> getValue, E defaultValue) {
        assert clazz != null;
        assert getValue != null;

        if (!EnumCacheHolder.existCache(clazz)) {
            EnumCacheHolder.initCache(clazz);
        }

        List<Enum<?>> values = EnumCacheHolder.getCache(clazz).orElse(Collections.emptyList());
        for (Enum<?> value : values) {
            Object o = getValue.apply((E)value);
            if (Objects.equals(lookupValue, o)) {
                return (E)value;
            }
        }

        return defaultValue;
    }
}
