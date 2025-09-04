package yunjiao.springboot.autoconfigure.tika;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import yunjiao.springboot.autoconfigure.util.PropertyNameConsts;

/**
 * Tika配置文件
 *
 * @author yangyunjiao
 */
@Data
@ConfigurationProperties(prefix = PropertyNameConsts.PROPERTY_PREFIX_TIKA)
public class TikaProperties {
    /**
     * tika配置xml文件路径，如：classpath:tika/config.xml
     */
    private Resource configXmlFile;
}
