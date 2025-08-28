package yunjiao.springboot.extension.common.util;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link GsonUtils} 单元测试用例
 *
 * @author yangyunjiao
 */
public class GsonUtilsTest {
    // 测试用的简单对象
    @Data
    static class TestUser {
        // getters and setters
        private String name;
        private int age;
        private Date birthDate;

        public TestUser() {}

        public TestUser(String name, int age, Date birthDate) {
            this.name = name;
            this.age = age;
            this.birthDate = birthDate;
        }
    }

    @Test
    public void testToJson() {
        TestUser user = new TestUser("张三", 25, new Date(91, 0, 1));
        String json = GsonUtils.toJson(user);

        assertThat(json).contains("\"name\": \"张三\"");
        assertThat(json).contains("\"age\": 25");
        assertThat(json).contains("\"birthDate\": ");
    }

    @Test
    public void testFromJson() {
        String json = "{\"name\":\"李四\",\"age\":30,\"birthDate\":\"1991-01-01 00:00:00\"}";
        TestUser user = GsonUtils.fromJson(json, TestUser.class);

        assertEquals("李四", user.getName());
        assertEquals(30, user.getAge());
        assertNotNull(user.getBirthDate());
    }

    @Test
    public void testFromJsonWithReader() {
        String json = "{\"name\":\"王五\",\"age\":35,\"birthDate\":\"1991-01-01 00:00:00\"}";
        StringReader reader = new StringReader(json);
        TestUser user = GsonUtils.fromJson(reader, TestUser.class);

        assertEquals("王五", user.getName());
        assertEquals(35, user.getAge());
        assertNotNull(user.getBirthDate());
    }

    @Test
    public void testToList() {
        String json = "[{\"name\":\"张三\",\"age\":25,\"birthDate\":\"1991-01-01 00:00:00\"}," +
                "{\"name\":\"李四\",\"age\":30,\"birthDate\":\"1991-01-01 00:00:00\"}]";

        List<TestUser> users = GsonUtils.toList(json, TestUser.class);

        assertEquals(2, users.size());
        assertEquals("张三", users.get(0).getName());
        assertEquals("李四", users.get(1).getName());
    }

    @Test
    public void testToMap() {
        String json = "{\"user1\":{\"name\":\"张三\",\"age\":25,\"birthDate\":\"1991-01-01 00:00:00\"}," +
                "\"user2\":{\"name\":\"李四\",\"age\":30,\"birthDate\":\"1991-01-01 00:00:00\"}}";

        Map<String, TestUser> userMap = GsonUtils.toMap(json, String.class, TestUser.class);

        assertEquals(2, userMap.size());
        assertTrue(userMap.containsKey("user1"));
        assertTrue(userMap.containsKey("user2"));
        assertEquals("张三", userMap.get("user1").getName());
        assertEquals("李四", userMap.get("user2").getName());
    }

    @Test
    public void testFormatJson() {
        String unformattedJson = "{\"name\":\"张三\",\"age\":25,\"birthDate\":\"1991-01-01 00:00:00\"}";
        String formattedJson = GsonUtils.formatJson(unformattedJson);

        assertTrue(formattedJson.contains("\n"));
        assertTrue(formattedJson.contains("  ")); // 检查是否有缩进
    }

    @Test
    public void testIsValidJson() {
        // 测试有效JSON
        assertTrue(GsonUtils.isValidJson("{\"name\":\"张三\"}"));
        assertTrue(GsonUtils.isValidJson("[1, 2, 3]"));

        // 测试无效JSON
        assertFalse(GsonUtils.isValidJson("{\"name\":\"张三\""));
        assertFalse(GsonUtils.isValidJson("invalid json"));
    }

    @Test
    public void testFromJsonWithType() {
        String json = "[{\"name\":\"张三\",\"age\":25,\"birthDate\":\"1991-01-01 00:00:00\"}," +
                "{\"name\":\"李四\",\"age\":30,\"birthDate\":\"1991-01-01 00:00:00\"}]";

        Type listType = TypeToken.getParameterized(List.class, TestUser.class).getType();
        List<TestUser> users = GsonUtils.fromJson(json, listType);

        assertEquals(2, users.size());
        assertEquals("张三", users.get(0).getName());
        assertEquals("李四", users.get(1).getName());
    }

    @Test
    public void testNullHandling() {
        // 测试null对象转JSON
        String json = GsonUtils.toJson(null);
        assertEquals("null", json);

        // 测试null字符串转对象
        TestUser user = GsonUtils.fromJson((String) null, TestUser.class);
        assertNull(user);

        // 测试空字符串转对象
        user = GsonUtils.fromJson("", TestUser.class);
        assertNull(user);
    }

    @Test
    public void testInvalidJsonConversion() {
        String invalidJson = "{\"name\":\"张三\",\"age\":}"; // 无效的JSON

        assertThatThrownBy(() -> GsonUtils.fromJson(invalidJson, TestUser.class))
                .isInstanceOf(JsonSyntaxException.class)
                .hasMessageContaining("Expected value at line");
    }
}
