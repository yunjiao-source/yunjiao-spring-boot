package yunjiao.springboot.extension.captcha.anji;

import com.anji.captcha.model.common.CaptchaTypeEnum;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import yunjiao.springboot.extension.common.captcha.CaptchaCategory;
import yunjiao.springboot.extension.common.captcha.CaptchaData;
import yunjiao.springboot.extension.common.captcha.Point;
import yunjiao.springboot.extension.common.util.GsonUtils;

import java.util.Base64;
import java.util.List;

/**
 * 滑块拼图验证码 服务
 *
 * @author yangyunjiao
 */
@Slf4j
public class ClickWorkCaptchaService extends BaseCaptchaService {
    /**
     * 拼图坐标允许误差偏移量
     */
    private final Integer slipOffset;

    /**
     * 构造器
     *
     * @param captchaService 验证码服务
     * @param captchaCacheService 验证码缓存
     * @param slipOffset 拼图坐标允许误差偏移量
     */
    public ClickWorkCaptchaService(CaptchaService captchaService, CaptchaCacheService captchaCacheService, Integer slipOffset) {
        super(captchaService, captchaCacheService);
        this.slipOffset = slipOffset;
    }


    @Override
    public boolean verify(String originalCode, String userCode) {
        try {
            List<Point> originalList = GsonUtils.toList(originalCode, Point.class);
            List<Point> userList = GsonUtils.toList(userCode, Point.class);

            if (originalList.size() != userList.size()) {
                return false;
            }

            boolean passed = true;
            for (int i = 0; i < originalList.size(); i++) {
                Point original = originalList.get(i);
                Point user = userList.get(i);
                passed = between(user.x(), original.x() - slipOffset, original.x() + slipOffset)
                        && between(user.y(), original.y() - slipOffset, original.y() + slipOffset);
                if (!passed) {
                    return false;
                }
            }
            return true;
        } catch (JsonSyntaxException e) {
            log.error("验证码格式异常", e);
        }

        return false;
    }

    @Override
    public CaptchaCategory getCategory() {
        return CaptchaCategory.clickWord;
    }

    @Override
    protected CaptchaTypeEnum getCaptchaType() {
        return CaptchaTypeEnum.CLICKWORD;
    }

    @Override
    protected CaptchaData convert(CaptchaData source, CaptchaVO target) {
        String captchaImageBase64 = target.getOriginalImageBase64();

        return source.captchaImage(Base64.getDecoder().decode(captchaImageBase64));
    }
}
