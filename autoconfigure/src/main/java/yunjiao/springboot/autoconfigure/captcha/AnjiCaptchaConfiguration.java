package yunjiao.springboot.autoconfigure.captcha;

import com.anji.captcha.model.common.Const;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.service.impl.CaptchaServiceFactory;
import com.anji.captcha.util.Base64Utils;
import com.anji.captcha.util.FileCopyUtils;
import com.anji.captcha.util.ImageUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;
import yunjiao.springboot.extension.captcha.anji.BlockPuzzleCaptchaService;
import yunjiao.springboot.extension.captcha.anji.ClickWorkCaptchaService;
import yunjiao.springboot.extension.captcha.anji.RotatePluzzleCaptchaService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Anji 验证码自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({CaptchaService.class})
@EnableConfigurationProperties({AnjiCaptchaProperties.class})
public class AnjiCaptchaConfiguration {
    /**
     * {@link PostConstruct} 注解方法
     */
    @PostConstruct
    public void postConstruct() {
        log.info("Anji Captcha Configuration");
    }

    @Bean
    CaptchaCacheService captchaCacheService(){
        CaptchaCacheService service = CaptchaServiceFactory.getCache("local");
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Captcha Cache Service:{}]", service);
        }
        return service;
    }

    @Bean
    CaptchaService captchaService(AnjiCaptchaProperties properties) {
        Properties config = new Properties();
        config.put(Const.CAPTCHA_WATER_MARK, properties.getWaterMark());
        config.put(Const.CAPTCHA_FONT_TYPE, properties.getClickWord().getFontType());
        config.put(Const.CAPTCHA_TYPE, "default");
        config.put(Const.CAPTCHA_INTERFERENCE_OPTIONS, properties.getBlockPuzzle().getInterferenceOptions());
        config.put(Const.ORIGINAL_PATH_JIGSAW, properties.getBlockPuzzle().getJigsaw());
        config.put(Const.ORIGINAL_PATH_PIC_CLICK, properties.getClickWord().getPicClick());
        config.put(Const.CAPTCHA_SLIP_OFFSET, properties.getBlockPuzzle().getSlipOffset());
        config.put(Const.CAPTCHA_AES_STATUS, "false");
        config.put(Const.CAPTCHA_WATER_FONT, properties.getWaterFont());


        config.put(Const.CAPTCHA_FONT_SIZE, properties.getClickWord().getFontSize());
        config.put(Const.CAPTCHA_FONT_STYLE, properties.getClickWord().getFontStyle().getMapping());
        config.put(Const.CAPTCHA_WORD_COUNT, properties.getClickWord().getClickWordCount());

        fixBugCache(config);
        fixBugResource(config);

        // 加载自定义
        initJigsawResource(properties.getBlockPuzzle().getJigsaw());
        initPicClickResource(properties.getClickWord().getPicClick());
        CaptchaService service = CaptchaServiceFactory.getInstance(config);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Captcha Service:{}]", service);
        }
        return service;
    }

    /**
     * 初始化时，加载默认资源执行多次， 资源重复加载。解决： 手工加载默认资源
     *
     * @param config 必须值
     */
    private void fixBugResource(Properties config) {
        // 框架问题: 方向反了
        config.put(Const.CAPTCHA_INIT_ORIGINAL, "true");

        // 加载默认资源
        ImageUtils.cacheImage(null, null, null);
    }

    /**
     * 初始化缓存回执行多次，造成CacheUtil中任务泄露。解决：停用任务，手工删除缓存
     *
     * @param config 必须值
     */
    private void fixBugCache(Properties config) {
        config.put(Const.CAPTCHA_CACHETYPE, "local");
        config.put(Const.CAPTCHA_TIMING_CLEAR_SECOND, "0");
    }

    @Bean
    yunjiao.springboot.extension.common.captcha.CaptchaService blockPuzzleCaptchaService(CaptchaService captchaService,
                                                                                         CaptchaCacheService captchaCacheService,
                                                                                         AnjiCaptchaProperties properties) {
        BlockPuzzleCaptchaService service = new BlockPuzzleCaptchaService(captchaService,
                captchaCacheService,
                properties.getBlockPuzzle().getSlipOffset());
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Block Puzzle CaptchaService:{}]", service);
        }
        return service;
    }

    @Bean
    yunjiao.springboot.extension.common.captcha.CaptchaService clickWorkCaptchaService(CaptchaService captchaService,
                                                                                         CaptchaCacheService captchaCacheService,
                                                                                         AnjiCaptchaProperties properties) {
        ClickWorkCaptchaService service = new ClickWorkCaptchaService(captchaService,
                captchaCacheService,
                properties.getBlockPuzzle().getSlipOffset());
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Click Work CaptchaService:{}]", service);
        }
        return service;
    }

    @Bean
    yunjiao.springboot.extension.common.captcha.CaptchaService rotatePluzzleCaptchaService(CaptchaService captchaService,
                                                                                         CaptchaCacheService captchaCacheService) {
        RotatePluzzleCaptchaService service = new RotatePluzzleCaptchaService(captchaService,
                captchaCacheService);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Rotate Pluzzle CaptchaService:{}]", service);
        }
        return service;
    }

    private void initJigsawResource(String jigsaw) {
        if (StringUtils.hasText(jigsaw) && jigsaw.startsWith("classpath")) {
            ImageUtils.cacheBootImage(getResourcesImagesFile(jigsaw + "/original/*.png"),
                    getResourcesImagesFile(jigsaw + "/slidingBlock/*.png"),
                    Collections.emptyMap());
        }
    }

    private void initPicClickResource(String picClick) {
        if (StringUtils.hasText(picClick) && picClick.startsWith("classpath")) {
            ImageUtils.cacheBootImage(Collections.emptyMap(), Collections.emptyMap(),
                    getResourcesImagesFile(picClick + "/*.png"));
        }
    }

    public Map<String, String> getResourcesImagesFile(String path) {
        Map<String, String> imgMap = new HashMap<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources(path);
            for (Resource resource : resources) {
                byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
                String string = Base64Utils.encodeToString(bytes);
                String filename = resource.getFilename();
                imgMap.put(filename, string);
            }
        } catch (Exception e) {
            log.error("初始化Anji验证码码资源异常", e);
        }
        return imgMap;
    }
}
