package yunjiao.springboot.extension.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * {@link Gson} 工具箱
 *
 * @author yangyunjiao
 */
public final class GsonUtils {

    private static final Gson GSON = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss") // 设置日期格式
            .disableHtmlEscaping() // 禁止转义HTML标签
            .setPrettyPrinting() // 格式化输出
            .create();

    private GsonUtils() {
        // 工具类，防止实例化
    }

    /**
     * 将对象转换为JSON字符串
     * @param obj 要转换的对象
     * @return JSON字符串
     */
    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    /**
     * 将JSON字符串转换为指定类型的对象
     * @param json JSON字符串
     * @param clazz 目标类型
     * @param <T> 泛型类型
     * @return 转换后的对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    /**
     * 将JSON字符串转换为指定Type的对象
     * @param json JSON字符串
     * @param type 目标Type
     * @param <T> 泛型类型
     * @return 转换后的对象
     */
    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    /**
     * 从Reader读取JSON并转换为指定类型的对象
     * @param reader Reader对象
     * @param clazz 目标类型
     * @param <T> 泛型类型
     * @return 转换后的对象
     */
    public static <T> T fromJson(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    /**
     * 将JSON字符串转换为List
     * @param json JSON字符串
     * @param clazz List中元素的类型
     * @param <T> 泛型类型
     * @return 转换后的List
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        return GSON.fromJson(json, type);
    }

    /**
     * 将JSON字符串转换为Map
     * @param json JSON字符串
     * @param keyClass Map中key的类型
     * @param valueClass Map中value的类型
     * @param <K> key的泛型类型
     * @param <V> value的泛型类型
     * @return 转换后的Map
     */
    public static <K, V> Map<K, V> toMap(String json, Class<K> keyClass, Class<V> valueClass) {
        Type type = TypeToken.getParameterized(Map.class, keyClass, valueClass).getType();
        return GSON.fromJson(json, type);
    }

    /**
     * 格式化JSON字符串
     * @param json 未格式化的JSON字符串
     * @return 格式化后的JSON字符串
     */
    public static String formatJson(String json) {
        JsonElement jsonElement = JsonParser.parseString(json);
        return GSON.toJson(jsonElement);
    }

    /**
     * 判断字符串是否为有效的JSON
     * @param json 要检查的字符串
     * @return 是否为有效的JSON
     */
    public static boolean isValidJson(String json) {
        try {
            JsonElement jsonElement = JsonParser.parseString(json);
            return jsonElement != null;
        } catch (Exception e) {
            return false;
        }
    }
}
