package io.yunjiao.extension.apjson.orm;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * {@link LinkedHashMap} 子类
 *
 * @author yangyunjiao
 */
public class GsonMap<K, V> extends LinkedHashMap<K, V> {
    public static <K, V> GsonMap<K, V> of(Map<K, V> map) {
        GsonMap<K, V> gsonMap = new GsonMap<>();
        if (Objects.nonNull(map)) {
            gsonMap.putAll(map);
        }
        return gsonMap;
    }

    /**
     * 获取map值
     * @param key 主键
     * @return map值
     */
    public Map<K, V> getJSONObject(K key) {
        return (Map)get(key);
    }

    /**
     * 获取字符值
     * @param key 主键
     * @return 字符值
     */
    public String getString(K key) {
        V value = get(key);
        if (Objects.nonNull(value)) {
            return value.toString();
        }

        return null;
    }

    /**
     * 获取整型值
     * @param key 主键
     * @return 整型值
     */
    public int getIntValue(K key) {
        V value = get(key);
        if (Objects.nonNull(value)) {
            return Integer.parseInt(value.toString());
        }

        return 0;
    }

    /**
     * 获取布尔值
     * @param key 主键
     * @return 布尔值
     */
    public Boolean getBoolean(K key) {
        V value = get(key);
        if (Objects.nonNull(value)) {
            return Boolean.valueOf(value.toString());
        }

        return null;
    }

    /**
     * 获取布尔值
     * @param key 主键
     * @return 布尔值
     */
    public boolean getBooleanValue(K key) {
        V value = get(key);
        if (Objects.nonNull(value)) {
            return Boolean.parseBoolean(value.toString());
        }

        return false;
    }

    /**
     * 获取长整型值
     * @param key 主键
     * @return 长整型值
     */
    public long getLongValue(K key) {
        V value = get(key);
        if (Objects.nonNull(value)) {
            return Long.parseLong(value.toString());
        }

        return 0L;
    }
}
