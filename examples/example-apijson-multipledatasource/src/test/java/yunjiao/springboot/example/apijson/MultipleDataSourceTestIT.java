package yunjiao.springboot.example.apijson;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.reactive.function.client.WebClient;
import yunjiao.springboot.extension.common.util.GsonUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 演示
 *
 * @author yangyunjiao
 */
public class MultipleDataSourceTestIT {
    private final WebClient commonClient = WebClient.builder()
            .baseUrl("http://localhost:8080/api-json/common")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @Test
    void test1() {
        String data = commonClient.post()
                .uri("/get")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(readJson("1. 第一个查询.json"))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        Object o = GsonUtils.fromJson(data, Object.class);
        System.out.println(o);
    }

    @Test
    void test2() {
        String data = commonClient.post()
                .uri("/get")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(readJson("2. 指定数据源查询.json"))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        Object o = GsonUtils.fromJson(data, Object.class);
        System.out.println(o);
    }

    private String readJson(String fileName)  {
        ClassPathResource resource = new ClassPathResource("json/" + fileName);
        byte[] bytes = new byte[0];
        try {
            bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
