package yunjiao.springboot.example.captcha;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import yunjiao.springboot.autoconfigure.captcha.CaptchaServiceFactory;
import yunjiao.springboot.extension.captcha.cache.CaptchaCache;
import yunjiao.springboot.extension.captcha.cache.CaptchaCacheFactory;
import yunjiao.springboot.extension.common.captcha.*;

/**
 * 接口
 *
 * @author yangyunjiao
 */
@RestController
@RequiredArgsConstructor
public class CaptchaController {
    private final CaptchaServiceFactory factory;
    private final CaptchaCache cache = CaptchaCacheFactory.getInstance();

    @GetMapping("/captcha")
    public CaptchaReponse get(@RequestParam(name = "category") CaptchaCategory category) {
        CaptchaService service = factory.findService(category);
        CaptchaData data = service.draw();

        CaptchaReponse reponse = new CaptchaReponse();
        reponse.setKey(data.key());
        reponse.setCategory(service.getCategory());
        reponse.setCaptchaImageBase64(data.captchaImageBase64Url());
        reponse.setBackgroundImageBase64(data.backgroundImageBase64Url());

        cache.put(data.key(), data.code());
        System.out.println("code=" + data.code());
        return reponse;
    }

    @PostMapping("/captcha")
    public String post(@RequestBody CaptchaValidate validate) {
        String cacheCode = cache.get(validate.getKey()).orElse(null);
        if (!StringUtils.hasText(cacheCode)) {
            return "验证码校验失败";
        }

        CaptchaService service = factory.findService(validate.getCategory());
        boolean pass = service.verify(cacheCode, validate.getCode());
        if (pass) {
            return "验证码校验成功";
        } else {
            return "验证码校验失败";
        }
    }
}
