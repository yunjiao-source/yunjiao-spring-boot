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

/**
 * 滑动验证码 服务
 *
 * @author yangyunjiao
 */
public class BlockPuzzleCaptchaService extends BaseCaptchaService {
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
    public BlockPuzzleCaptchaService(CaptchaService captchaService, CaptchaCacheService captchaCacheService, Integer slipOffset) {
        super(captchaService, captchaCacheService);
        this.slipOffset = slipOffset;
    }


    @Override
    public boolean verify(String originalCode, String userCode) {
        Point original = GsonUtils.fromJson(originalCode, Point.class);
        Point user = GsonUtils.fromJson(userCode, Point.class);

        return between(user.x(), original.x() - slipOffset, original.x() + slipOffset)
                && between(user.y(), original.y() - slipOffset, original.y() + slipOffset);
    }

    @Override
    public CaptchaCategory getCategory() {
        return CaptchaCategory.blockPuzzle;
    }

    @Override
    protected CaptchaTypeEnum getCaptchaType() {
        return CaptchaTypeEnum.BLOCKPUZZLE;
    }

    @Override
    protected CaptchaData convert(CaptchaData source, CaptchaVO target) {
        String backgroundImageBase64 = target.getOriginalImageBase64();
        String captchaImageBase64 = target.getJigsawImageBase64();

        return source.backgroundImage(Base64.getDecoder().decode(backgroundImageBase64))
                .captchaImage(Base64.getDecoder().decode(captchaImageBase64));
    }
}
