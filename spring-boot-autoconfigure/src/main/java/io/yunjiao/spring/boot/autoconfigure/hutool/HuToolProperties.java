package io.yunjiao.spring.boot.autoconfigure.hutool;

import io.yunjiao.spring.boot.autoconfigure.util.PropertyNameConsts;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * HuTool 配置属性
 *
 * @author yangyunjiao
 */
@Data
@ConfigurationProperties(prefix = PropertyNameConsts.PROPERTY_PREFIX_HUTOOL)
public class HuToolProperties {
    private Boolean snowflake;
}
