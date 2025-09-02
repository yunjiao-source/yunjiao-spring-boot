package yunjiao.springboot.example.tika;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 控制器
 *
 * @author yangyunjiao
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("tika")
public class TikaController {
    private final TikaService tikaService;

    @PostMapping("/extract-text")
    public ResponseEntity<String> extractText(@RequestParam("file") MultipartFile file) {
        String extractedText = tikaService.extractText(file);
        return ResponseEntity.ok(extractedText);
    }

    @PostMapping("/detect-mime-type")
    public ResponseEntity<String> detectMetadata(@RequestParam("file") MultipartFile file) {
        String mimeType = tikaService.detectMimeType(file);
        return ResponseEntity.ok(mimeType);
    }
}
