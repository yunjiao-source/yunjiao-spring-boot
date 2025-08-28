package yunjiao.springboot.extension.captcha.anji;

import com.anji.captcha.model.common.CaptchaTypeEnum;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;
import yunjiao.springboot.extension.common.captcha.CaptchaCategory;
import yunjiao.springboot.extension.common.captcha.CaptchaData;

import java.util.Base64;

/**
 * 旋转拼图验证码 服务
 *
 * @author yangyunjiao
 */
public class RotatePluzzleCaptchaService extends BaseCaptchaService {

    public RotatePluzzleCaptchaService(CaptchaService captchaService, CaptchaCacheService captchaCacheService) {
        super(captchaService, captchaCacheService);
    }

    @Override
    public boolean verify(String originalCode, String userCode) {
        return false;
    }

    @Override
    public CaptchaCategory getCategory() {
        return CaptchaCategory.rotatePuzzle;
    }

    @Override
    protected CaptchaTypeEnum getCaptchaType() {
        return CaptchaTypeEnum.ROTATEPUZZLE;
    }

    @Override
    protected CaptchaData convert(CaptchaData data, CaptchaVO vo) {
        String captchaImageBase64 = vo.getOriginalImageBase64();

        return data.captchaImage(Base64.getDecoder().decode(captchaImageBase64));
    }
}
