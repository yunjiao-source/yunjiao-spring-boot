package yunjiao.springboot.autoconfigure.tika;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.Parser;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;
import yunjiao.springboot.extension.tika._TIKA;

import java.io.IOException;

/**
 * 验证码 自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass({_TIKA.class})
@EnableConfigurationProperties({TikaProperties.class})
public class TikaAutoConfiguration {
    /**
     * {@link PostConstruct} 注解方法
     */
    @PostConstruct
    public void postConstruct() {
        log.info("Tika Auto Configuration");
    }

    @Bean
    Tika tika(TikaProperties properties) {
        TikaConfig config = createTikaConfig(properties.getConfigXmlFile());

        Detector detector = config.getDetector();
        Parser autoDetectParser = new AutoDetectParser(config);
        Tika bean = new Tika(detector, autoDetectParser);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Tika -> {}]", bean);
        }

        return bean;
    }

    private TikaConfig createTikaConfig(Resource res) {
        if (res != null) {
            if (log.isDebugEnabled()) {
                log.debug("Loading tika config file: {}", res.getFilename());
            }
            try {
                return new TikaConfig(res.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException("Unable to read default configuration", e);
            } catch (TikaException | SAXException e) {
                throw new RuntimeException("Unable to access default configuration", e);
            }
        }

        return TikaConfig.getDefaultConfig();
    }

}
