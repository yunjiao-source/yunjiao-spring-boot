package yunjiao.springboot.extension.captcha.anji;

import com.anji.captcha.model.common.CaptchaTypeEnum;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;
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
public class ClickWorkCaptchaService extends BaseCaptchaService {
    private final Integer slipOffset;

    public ClickWorkCaptchaService(CaptchaService captchaService, CaptchaCacheService captchaCacheService, Integer slipOffset) {
        super(captchaService, captchaCacheService);
        this.slipOffset = slipOffset;
    }


    @Override
    public boolean verify(String originalCode, String userCode) {
        List<Point> originalList = GsonUtils.toList(originalCode, Point.class);
        List<Point> userList = GsonUtils.toList(userCode, Point.class);
        if (originalList.size() != userList.size()) {
            return false;
        }

        for (int i = 0; i < originalList.size(); i++) {
            Point original = originalList.get(i);
            Point user = userList.get(i);
            boolean passed = between(user.x(), original.x() - slipOffset, original.x() + slipOffset)
                    && between(user.y(), original.y() - slipOffset, original.y() + slipOffset);
            if (!passed) {
                return false;
            }
        }
        return true;
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
    protected CaptchaData convert(CaptchaData data, CaptchaVO vo) {
        String captchaImageBase64 = vo.getOriginalImageBase64();

        return data.captchaImage(Base64.getDecoder().decode(captchaImageBase64));
    }
}
