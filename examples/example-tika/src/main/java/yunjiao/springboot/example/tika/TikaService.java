package yunjiao.springboot.example.tika;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * tika服务
 *
 * @author yangyunjiao
 */
@Service
@RequiredArgsConstructor
public class TikaService {
    private final Tika tika;

    /**
     * 提取文件中的文本内容
     */
    @SneakyThrows
    public String extractText(MultipartFile file) {
        // parseToString 方法内部会自动关闭 InputStream
        return tika.parseToString(file.getInputStream());
    }


    /**
     * 检测文件的 MIME 类型（根据内容而非扩展名）
     */
    @SneakyThrows
    public String detectMimeType(MultipartFile file){
        return tika.detect(file.getInputStream());
    }
}
