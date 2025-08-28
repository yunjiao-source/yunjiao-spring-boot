package yunjiao.springboot.extension.captcha.anji;

import com.anji.captcha.model.common.CaptchaTypeEnum;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaCacheService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import yunjiao.springboot.extension.captcha.CaptchaException;
import yunjiao.springboot.extension.common.captcha.CaptchaData;
import yunjiao.springboot.extension.common.captcha.CaptchaService;

/**
 * 抽象的验证码
 *
 * @author yangyunjiao
 */
@Getter
@RequiredArgsConstructor
public abstract class BaseCaptchaService implements CaptchaService {
    protected static final String CACHE_TYPE = "local";

    protected static final String REDIS_CAPTCHA_KEY = "RUNNING:CAPTCHA:%s";

    private final com.anji.captcha.service.CaptchaService captchaService;

    private final CaptchaCacheService captchaCacheService;

    protected abstract CaptchaTypeEnum getCaptchaType();

    protected abstract CaptchaData convert(CaptchaData data, CaptchaVO vo);

    protected boolean between(int target, int start, int end) {
        return start <= target && target <= end;
    }

    @Override
    public CaptchaData draw() {
        // 准备参数
        CaptchaVO vo = new CaptchaVO();
        vo.setCaptchaType(getCaptchaType().getCodeValue());

        // 生成验证码
        ResponseModel model = captchaService.get(vo);
        if (!model.isSuccess()) {
            throw new CaptchaException(model.getRepMsg());
        }

        CaptchaVO captcha = (CaptchaVO)model.getRepData();
        String token = captcha.getToken();
        String codeKey = String.format(REDIS_CAPTCHA_KEY, token);

        String code = captchaCacheService.get(codeKey);
        if (StringUtils.hasText(code)) {
            captchaCacheService.delete(codeKey);
        }

        return convert(new CaptchaData().key(token).code(code).category(getCategory()), captcha);
    }
}
