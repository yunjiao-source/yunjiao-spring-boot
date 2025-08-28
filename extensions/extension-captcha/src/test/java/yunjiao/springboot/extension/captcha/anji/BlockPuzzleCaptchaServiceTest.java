package yunjiao.springboot.extension.captcha.anji;

import com.anji.captcha.model.common.Const;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.service.impl.CaptchaServiceFactory;
import org.junit.jupiter.api.Test;
import yunjiao.springboot.extension.common.captcha.CaptchaCategory;
import yunjiao.springboot.extension.common.captcha.CaptchaData;
import yunjiao.springboot.extension.common.captcha.Point;
import yunjiao.springboot.extension.common.util.GsonUtils;

import java.awt.*;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link BlockPuzzleCaptchaService} 单元测试用例
 *
 * @author yangyunjiao
 */
public class BlockPuzzleCaptchaServiceTest {
    private static final BlockPuzzleCaptchaService blockPuzzleCaptchaService;

    static {
        Properties config = new Properties();
        config.put(Const.CAPTCHA_CACHETYPE, "local");
        config.put(Const.CAPTCHA_WATER_MARK, "我的水印");
        config.put(Const.CAPTCHA_FONT_TYPE, "WenQuanZhengHei.ttf");
        config.put(Const.CAPTCHA_TYPE, "default");
        config.put(Const.CAPTCHA_INTERFERENCE_OPTIONS, "0");
        config.put(Const.ORIGINAL_PATH_JIGSAW, "");
        config.put(Const.ORIGINAL_PATH_PIC_CLICK, "");
        config.put(Const.CAPTCHA_SLIP_OFFSET, "5");
        config.put(Const.CAPTCHA_AES_STATUS, "false");
        config.put(Const.CAPTCHA_WATER_FONT, "WenQuanZhengHei.ttf");
        // CacheUtil的定时任务存在泄露的问题
        config.put(Const.CAPTCHA_TIMING_CLEAR_SECOND, "0");

        config.put(Const.CAPTCHA_FONT_SIZE, "25");
        config.put(Const.CAPTCHA_FONT_STYLE, Font.BOLD);
        config.put(Const.CAPTCHA_WORD_COUNT, "4");

        CaptchaService captchaService = CaptchaServiceFactory.getInstance(config);
        CaptchaCacheService captchaCacheService = CaptchaServiceFactory.getCache("local");
        blockPuzzleCaptchaService = new BlockPuzzleCaptchaService(captchaService,
                captchaCacheService,
                6);
    }

    @Test
    void whenDraw_thenOk() {
        CaptchaData data = blockPuzzleCaptchaService.draw();
        assertThat(data.key()).isNotBlank();
        assertThat(data.backgroundImage()).isNotNull();
        assertThat(data.captchaImage()).isNotNull();
        assertThat(data.category()).isEqualTo(CaptchaCategory.blockPuzzle);
    }

    @Test
    void givenPoint_whenVerify_thenOK() {
        CaptchaData data = blockPuzzleCaptchaService.draw();
        assertThat(blockPuzzleCaptchaService.verify(data.code(), data.code())).isTrue();

        // 范围内
        Point original = GsonUtils.fromJson(data.code(), Point.class);
        String userCode = GsonUtils.toJson(new Point(original.x() - 5, original.y() + 5));
        assertThat(blockPuzzleCaptchaService.verify(data.code(), userCode)).isTrue();

        // x超出范围
        userCode = GsonUtils.toJson(new Point(original.x() - 7, original.y()));
        assertThat(blockPuzzleCaptchaService.verify(data.code(), userCode)).isFalse();

        // y超出范围
        userCode = GsonUtils.toJson(new Point(original.x(), original.y() + 7));
        assertThat(blockPuzzleCaptchaService.verify(data.code(), userCode)).isFalse();
    }


}
