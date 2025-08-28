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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link ClickWorkCaptchaService} 单元测试用例
 *
 * @author yangyunjiao
 */
public class ClickWorkCaptchaServiceTest {
    private static final ClickWorkCaptchaService clickWorkCaptchaService;

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
        clickWorkCaptchaService = new ClickWorkCaptchaService(captchaService,
                captchaCacheService,
                6);
    }

    @Test
    void whenDraw_thenOk() {
        CaptchaData data = clickWorkCaptchaService.draw();
        assertThat(data.key()).isNotBlank();
        assertThat(data.backgroundImage()).isNull();
        assertThat(data.captchaImage()).isNotNull();
        assertThat(data.category()).isEqualTo(CaptchaCategory.clickWord);
    }

    @Test
    void givenPoint_whenVerify_thenOK() {
        CaptchaData data = clickWorkCaptchaService.draw();
        assertThat(clickWorkCaptchaService.verify(data.code(), data.code())).isTrue();

        // 范围内
        List<Point> originalList = GsonUtils.toList(data.code(), Point.class);
        List<Point> userList = new ArrayList<>();
        for (int i = 0; i < originalList.size(); i++) {
            Point original = originalList.get(i);
            Point newPoint = new Point(original.x() - i, original.y() + i);
            userList.add(newPoint);
        }
        assertThat(clickWorkCaptchaService.verify(data.code(), GsonUtils.toJson(userList))).isTrue();

        // x超出范围
        userList.clear();
        for (int i = 0; i < originalList.size(); i++) {
            Point original = originalList.get(i);
            Point newPoint = new Point(original.x() - i*4, original.y());
            userList.add(newPoint);
        }
        assertThat(clickWorkCaptchaService.verify(data.code(), GsonUtils.toJson(userList))).isFalse();

        // y超出范围
        userList.clear();
        for (int i = 0; i < originalList.size(); i++) {
            Point original = originalList.get(i);
            Point newPoint = new Point(original.x(), original.y() + i*4);
            userList.add(newPoint);
        }
        assertThat(clickWorkCaptchaService.verify(data.code(), GsonUtils.toJson(userList))).isFalse();
    }


}
