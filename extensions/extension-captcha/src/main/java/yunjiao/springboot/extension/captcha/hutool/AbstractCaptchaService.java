package yunjiao.springboot.extension.captcha.hutool;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import yunjiao.springboot.extension.captcha.CaptchaException;
import yunjiao.springboot.extension.common.algorithm.GaussianBlur;
import yunjiao.springboot.extension.common.captcha.CaptchaData;
import yunjiao.springboot.extension.common.captcha.CaptchaService;

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
     * 获取参数
     * @return 校验时是否忽略大小写
     */
    protected abstract Boolean getValidIgnoreCase();

    /**
     * 获取参数
     * @return 模糊度
     */
    protected abstract Integer getFuzziness();

    /**
     * 模糊度处理
     *
     * @param image 必须值
     * @return 模糊后的图片
     */
    protected BufferedImage handleFuzziness(BufferedImage image) {
        Integer fuzziness = getFuzziness();
        if (fuzziness != null && fuzziness > 0) {
            return GaussianBlur.execute(image, fuzziness);
        }
        return image;
    }

    /**
     * 创建验证码信息
     *
     * @param code 码，必须值
     * @param image 必须值
     * @return 验证码信息
     */
    protected CaptchaData createCaptchaData(String code, BufferedImage image) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImgUtil.writePng(image, out);
            byte[] imageBytes = out.toByteArray();

            return new CaptchaData()
                    .key(IdUtil.fastSimpleUUID())
                    .code(code)
                    .category(getCategory())
                    .captchaImage(imageBytes);
        } catch (IOException e) {
            throw new CaptchaException("生成验证码图片异常", e);
        }
    }

    @Override
    public boolean verify(String originalCode, String userCode) {
        if(StringUtils.hasText(originalCode) && StringUtils.hasText(userCode)) {
            Boolean ignoreCase = getValidIgnoreCase();
            if (Boolean.TRUE.equals(ignoreCase)) {
                return originalCode.equalsIgnoreCase(userCode);
            } else {
                return originalCode.equals(userCode);
            }
        }

        return false;
    }
}
