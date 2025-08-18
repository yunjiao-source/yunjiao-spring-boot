package io.yunjiao.springboot.autoconfigure.hutool;

import io.yunjiao.springboot.autoconfigure.util.PropertyNameConsts;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Hutool 配置属性
 *
 * @author yangyunjiao
 */
@Data
@ConfigurationProperties(prefix = PropertyNameConsts.PROPERTY_PREFIX_HUTOOL)
public class HutoolProperties {
    private Boolean snowflake;
}
