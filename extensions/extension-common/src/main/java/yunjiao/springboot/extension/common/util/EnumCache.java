package yunjiao.springboot.extension.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举缓存
 * <p></p>
 * <p>
 * 参考文章：如何高效优雅的使用java枚举
 *
 * @author yangyunjiao
 */
public class EnumCache {

    /**
     * 以枚举任意值构建的缓存结构
     **/
    static final Map<Class<? extends Enum<?>>, Map<Object, Enum<?>>> CACHE_BY_VALUE = new ConcurrentHashMap<>();

    /**
     * 以枚举名称构建的缓存结构
     **/
    static final Map<Class<? extends Enum<?>>, Map<Object, Enum<?>>> CACHE_BY_NAME = new ConcurrentHashMap<>();

    /**
     * 枚举静态块加载标识缓存结构
     */
    static final Map<Class<? extends Enum<?>>, Boolean> LOADED = new ConcurrentHashMap<>();

    /**
     * 锁对象
     */
    private static final Object lock = new Object();

    /**
     * 以枚举名称构建缓存，在枚举的静态块里面调用
     *
     * @param clazz 必须值
     * @param values 必须值
     * @param <E> 枚举类型
     */
    public static <E extends Enum<?>> void registerByName(Class<E> clazz, E[] values) {
        Map<Object, Enum<?>> map = new ConcurrentHashMap<>();
        for (E v : values) {
            map.put(v.name(), v);
        }
        CACHE_BY_NAME.put(clazz, map);
    }

    /**
     * 以枚举转换出的任意值构建缓存，在枚举的静态块里面调用
     *
     * @param clazz 必须值
     * @param values 必须值
     * @param enumMapping 必须值
     * @param <E> 枚举类型
     */
    public static <E extends Enum<?>> void registerByValue(Class<E> clazz, E[] values, EnumMapping<E> enumMapping) {
        if (CACHE_BY_VALUE.containsKey(clazz)) {
            throw new RuntimeException(String.format("枚举%s已经构建过value缓存,不允许重复构建", clazz.getSimpleName()));
        }
        Map<Object, Enum<?>> map = new ConcurrentHashMap<>();
        for (E v : values) {
            Object value = enumMapping.value(v);
            if (map.containsKey(value)) {
                throw new RuntimeException(String.format("枚举%s存在相同的值%s映射同一个枚举%s.%s", clazz.getSimpleName(), value, clazz.getSimpleName(), v));
            }
            map.put(value, v);
        }
        CACHE_BY_VALUE.put(clazz, map);
    }

    /**
     * 从以枚举名称构建的缓存中通过枚举名获取枚举
     *
     * @param clazz 必须值
     * @param name 可以空
     * @return 实例
     * @param <E> 枚举类型
     */
    public static <E extends Enum<?>> E findByName(Class<E> clazz, String name) {
        return find(clazz, name, CACHE_BY_NAME, null);
    }

    /**
     * 从以枚举名称构建的缓存中通过枚举名获取枚举
     *
     * @param clazz 必须值
     * @param name 可以空
     * @param defaultEnum 可以空
     * @param <E> 枚举类型
     * @return 实例
     */
    public static <E extends Enum<?>> E findByName(Class<E> clazz, String name, E defaultEnum) {
        return find(clazz, name, CACHE_BY_NAME, defaultEnum);
    }

    /**
     * 从以枚举转换值构建的缓存中通过枚举转换值获取枚举
     *
     * @param clazz 必须值
     * @param value 可以空
     * @param <E> 枚举类型
     * @return 实例
     */
    public static <E extends Enum<?>> E findByValue(Class<E> clazz, Object value) {
        return find(clazz, value, CACHE_BY_VALUE, null);
    }

    /**
     * 从以枚举转换值构建的缓存中通过枚举转换值获取枚举
     *
     * @param clazz 必须值
     * @param value 可以空
     * @param defaultEnum 可以空
     * @param <E> 枚举类型
     * @return 实例
     */
    public static <E extends Enum<?>> E findByValue(Class<E> clazz, Object value, E defaultEnum) {
        return find(clazz, value, CACHE_BY_VALUE, defaultEnum);
    }

    /**
     *
     * @param clazz 必须值
     * @param obj 可以空
     * @param cache 必须值
     * @param defaultEnum 可以空
     * @return 实例
     * @param <E> 枚举类型
     */
    @SuppressWarnings({"all"})
    private static <E extends Enum<?>> E find(Class<E> clazz, Object obj, Map<Class<? extends Enum<?>>, Map<Object, Enum<?>>> cache, E defaultEnum) {
        Map<Object, Enum<?>> map = cache.get(clazz);
        if (map == null) {
            executeEnumStatic(clazz);// 触发枚举静态块执行
            map = cache.get(clazz);// 执行枚举静态块后重新获取缓存
        }
        if (map == null) {
            String msg = null;
            if (cache == CACHE_BY_NAME) {
                msg = String.format(
                        "枚举%s还没有注册到枚举缓存中，请在%s.static代码块中加入如下代码 : EnumCache.registerByName(%s.class, %s.values());",
                        clazz.getSimpleName(),
                        clazz.getSimpleName(),
                        clazz.getSimpleName(),
                        clazz.getSimpleName()
                );
            }
            if (cache == CACHE_BY_VALUE) {
                msg = String.format(
                        "枚举%s还没有注册到枚举缓存中，请在%s.static代码块中加入如下代码 : EnumCache.registerByValue(%s.class, %s.values(), %s::getXxx);",
                        clazz.getSimpleName(),
                        clazz.getSimpleName(),
                        clazz.getSimpleName(),
                        clazz.getSimpleName(),
                        clazz.getSimpleName()
                );
            }
            throw new RuntimeException(msg);
        }
        if (obj == null) {
            return defaultEnum;
        }
        Enum<?> result = map.get(obj);
        return result == null ? defaultEnum : (E) result;
    }

    /**
     *
     * @param clazz 必须值
     * @param <E> 枚举类型
     */
    private static <E extends Enum<?>> void executeEnumStatic(Class<E> clazz) {
        if (!LOADED.containsKey(clazz)) {
            synchronized (lock) {
                if (!LOADED.containsKey(clazz)) {
                    try {
                        // 目的是让枚举类的static块运行，static块没有执行完是会阻塞在此的
                        Class.forName(clazz.getName());
                        LOADED.put(clazz, true);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    /**
     * 枚举缓存映射器函数式接口
     */
    @FunctionalInterface
    public interface EnumMapping<E extends Enum<?>> {
        /**
         * 自定义映射器
         *
         * @param e 枚举
         * @return 映射关系，最终体现到缓存中
         */
        Object value(E e);
    }

}
