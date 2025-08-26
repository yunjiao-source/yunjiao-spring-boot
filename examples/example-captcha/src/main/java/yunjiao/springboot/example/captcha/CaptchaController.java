package yunjiao.springboot.example.captcha;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import yunjiao.springboot.autoconfigure.captcha.CaptchaServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
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
    private final TimedCache<String, String> timedCache = CacheUtil.newTimedCache(30 * 1000);

    @GetMapping("/captcha")
    public CaptchaReponse get(@RequestParam(name = "category") CaptchaCategory category) {
        CaptchaService service = factory.findService(category);
        CaptchaData data = service.draw();

        CaptchaReponse reponse = new CaptchaReponse();
        reponse.setKey(data.getKey());
        reponse.setCategory(service.getCategory());
        reponse.setCaptchaImageBase64(data.getCaptchaImageBase64());

        timedCache.put(data.getKey(), data.getCode());
        System.out.println("code=" + data.getCode());
        return reponse;
    }

    @PostMapping("/captcha")
    public String post(@RequestBody CaptchaValidate validate) {
        String cacheCode = timedCache.get(validate.getKey());
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
