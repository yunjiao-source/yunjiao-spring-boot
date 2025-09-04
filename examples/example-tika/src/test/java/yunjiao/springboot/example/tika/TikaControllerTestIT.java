package yunjiao.springboot.example.tika;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 演示
 *
 * @author yangyunjiao
 */
public class TikaControllerTestIT {
    private final WebClient commonClient = WebClient.builder()
            .baseUrl("http://localhost:8080/tika")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @Test
    void testExtractTextWithText() {
        String fileName = "text.txt";
        String data = extractText(fileName);
        System.out.println(fileName + "=" + data);
    }

    @Test
    void testExtractTextWithImage() {
        String fileName = "image.png";
        String data = extractText(fileName);
        System.out.println(fileName + "=" + data);
    }

    @Test
    void testExtractTextWithPdf() {
        String fileName = "pdf.pdf";
        String data = extractText(fileName);
        System.out.println(fileName + "=" + data);
    }

    @Test
    void testExtractTextWithDoc() {
        String fileName = "docx.docx";
        String data = extractText(fileName);
        System.out.println(fileName + "=" + data);
    }

    @Test
    void testExtractTextWithXlsx() {
        String fileName = "xlsx.xlsx";
        String data = extractText(fileName);
        System.out.println(fileName + "=" + data);
    }

    @Test
    void detectMimeTypeText() {
        String fileName = "text.txt";
        String data = detectMimeType(fileName);
        System.out.println(fileName + "=" + data);
    }

    @Test
    void detectMimeTypePdf() {
        String fileName = "pdf.pdf";
        String data = detectMimeType(fileName);
        System.out.println(fileName + "=" + data);
    }


    @Test
    void detectMimeTypeImage() {
        String fileName = "image.png";
        String data = detectMimeType(fileName);
        System.out.println(fileName + "=" + data);
    }

    private String detectMimeType(String fileName) {
        MultiValueMap<String, Object> parts = createMultiValueMap(fileName);
        return commonClient.post()
                .uri("/detect-mime-type")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(parts))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String extractText(String fileName) {
        MultiValueMap<String, Object> parts = createMultiValueMap(fileName);
        return commonClient.post()
                .uri("/extract-text")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(parts))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private MultiValueMap<String, Object> createMultiValueMap(String fileName)  {
        ClassPathResource resource = new ClassPathResource(fileName);
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", resource);
        parts.add("fileName", fileName);
        return parts;
    }
}
