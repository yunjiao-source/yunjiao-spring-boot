package yunjiao.springboot.extension.captcha.hutool;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.IdUtil;
import yunjiao.springboot.extension.common.algorithm.GaussianBlur;
import yunjiao.springboot.extension.common.captcha.CaptchaData;
import yunjiao.springboot.extension.common.captcha.CaptchaService;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 抽象的验证码 服务
 *
 * @author yangyunjiao
 */
@Slf4j
public abstract class AbstractCaptchaService implements CaptchaService {

    /**
     * 获取 校验时是否忽略大小写
     * @return 校验时是否忽略大小写
     */
    protected abstract Boolean getValidIgnoreCase();

    protected abstract Integer getFuzziness();

    protected void handleFuzziness(BufferedImage image) {
        Integer fuzziness = getFuzziness();
        if (fuzziness != null && fuzziness > 0) {
            image = GaussianBlur.execute(image, fuzziness);
        }
    }

    protected CaptchaData createCaptchaData(String code, BufferedImage image) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImgUtil.writePng(image, out);
            byte[] imageBytes = out.toByteArray();

            return CaptchaData.builder()
                    .key(IdUtil.fastSimpleUUID())
                    .code(code)
                    .category(getCategory())
                    .captchaImage(imageBytes)
                    .build();
        } catch (IOException e) {
            throw new CaptchaException("生成验证码图片异常", e);
        }



    }

    @Override
    public boolean verify(Object orignalCode, Object userCode) {
        if(orignalCode == null || userCode == null) {
            return false;
        }

        if (orignalCode instanceof String orginalStr
                && userCode instanceof String userStr ) {
            // 是否忽略大写校验
            Boolean ignoreCase = getValidIgnoreCase();
            if (Boolean.TRUE.equals(ignoreCase)) {
                return orginalStr.equalsIgnoreCase(userStr);
            } else {
                return orginalStr.equals(userStr);
            }
        } else {
            log.error("验证码应该是字符串类型，实际是{}和{}类型", orignalCode.getClass().getSimpleName(),
                    userCode.getClass().getSimpleName());
        }
        return false;
    }
}
