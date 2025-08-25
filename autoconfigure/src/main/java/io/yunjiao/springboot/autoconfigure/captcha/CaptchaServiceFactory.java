package io.yunjiao.springboot.autoconfigure.captcha;

import io.yunjiao.extension.captcha.hutool.CaptchaException;
import io.yunjiao.extension.common.captcha.CaptchaCategory;
import io.yunjiao.extension.common.captcha.CaptchaService;
import io.yunjiao.extension.common.lang.EnumCache;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * 验证码服务工厂
 *
 * @author yangyunjiao
 */
@Getter
@RequiredArgsConstructor
public class CaptchaServiceFactory {
    private final Map<String, CaptchaService> services;

    /**
     * 根据分类代码，查找验证码服务
     *
     * @param categoryCode 必须值
     * @return 实例
     */
    public CaptchaService findService(String categoryCode) {
        CaptchaCategory category = EnumCache.findByValue(CaptchaCategory.class, categoryCode);
        if (category == null) {
            throw new CaptchaException("验证码分类代码不存在，代码是：" + categoryCode);
        }
        return findService(category);
    }

    /**
     * 根据分类，查找验证码服务
     *
     * @param category 分类
     * @return 实例
     */
    public CaptchaService findService(CaptchaCategory category) {
        CaptchaService service = services.get(category.getCode());
        if (service == null) {
            throw new CaptchaException("根据分类查找验证码服务未找到, 分类是：" + category);
        }

        return service;
    }
}
