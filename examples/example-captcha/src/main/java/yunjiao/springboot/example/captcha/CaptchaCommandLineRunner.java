package yunjiao.springboot.example.captcha;

import yunjiao.springboot.extension.common.captcha.CaptchaCategory;
import yunjiao.springboot.extension.common.captcha.CaptchaData;
import yunjiao.springboot.extension.common.captcha.CaptchaService;
import yunjiao.springboot.autoconfigure.captcha.CaptchaServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import yunjiao.springboot.extension.common.util.GsonUtils;

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
        CaptchaService circleService = factory.findService(CaptchaCategory.circle);
        CaptchaData data = circleService.draw();
        log.info(GsonUtils.toJson(data));
        saveImage(data);

        CaptchaService lineService = factory.findService(CaptchaCategory.line);
        data = lineService.draw();
        saveImage(data);

        CaptchaService gifService = factory.findService(CaptchaCategory.gif);
        data = gifService.draw();
        saveImage(data);

        CaptchaService shearService = factory.findService(CaptchaCategory.shear);
        data = shearService.draw();
        saveImage(data);

        CaptchaService blockPuzzleService = factory.findService(CaptchaCategory.blockPuzzle);
        data = blockPuzzleService.draw();
        saveImage(data);

        CaptchaService clickWordService = factory.findService(CaptchaCategory.clickWord);
        data = clickWordService.draw();
        saveImage(data);
    }

    private void saveImage(CaptchaData data) throws IOException {
        Path targetDir = Paths.get("target", "processed-images");
        if (!Files.exists(targetDir)) {
            Files.createDirectories(targetDir);
        }

        Path filePath = targetDir.resolve(data.category().name() + "-captcha." + data.category().getExt());
        Files.write(filePath, data.captchaImage());

        if (data.backgroundImage() != null) {
            filePath = targetDir.resolve(data.category().name() + "-background." + data.category().getExt());
            Files.write(filePath, data.backgroundImage());
        }
    }
}
