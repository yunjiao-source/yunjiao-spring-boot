package yunjiao.springboot.example.captcha;

import yunjiao.springboot.extension.common.captcha.CaptchaCategory;
import yunjiao.springboot.extension.common.captcha.CaptchaData;
import yunjiao.springboot.extension.common.captcha.CaptchaService;
import yunjiao.springboot.autoconfigure.captcha.CaptchaServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 示例
 *
 * @author yangyunjiao
 */
@Slf4j
@Component
public class CaptchaCommandLineRunner implements CommandLineRunner {
    @Autowired
    private CaptchaServiceFactory factory;

    @Override
    public void run(String... args) throws Exception {
        CaptchaService circleService = factory.findService(CaptchaCategory.hutoolCircle);
        CaptchaData data = circleService.draw();
        saveImage(data);

        CaptchaService lineService = factory.findService(CaptchaCategory.hutoolLine);
        data = lineService.draw();
        saveImage(data);

        CaptchaService gifService = factory.findService(CaptchaCategory.hutoolGif);
        data = gifService.draw();
        saveImage(data);

        CaptchaService shearService = factory.findService(CaptchaCategory.hutoolShear);
        data = shearService.draw();
        saveImage(data);
    }

    private void saveImage(CaptchaData data) throws IOException {
        Path targetDir = Paths.get("target", "processed-images");
        if (!Files.exists(targetDir)) {
            Files.createDirectories(targetDir);
        }

        Path filePath = targetDir.resolve(data.getCategory().getCode() + "." + data.getCategory().getExt());
        Files.write(filePath, data.getCaptchaImage());
    }
}
