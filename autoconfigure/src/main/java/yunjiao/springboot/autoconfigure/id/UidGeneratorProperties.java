package yunjiao.springboot.autoconfigure.id;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import yunjiao.springboot.autoconfigure.util.PropertyNameConsts;

/**
 * Uid-Generator 配置属性
 *
 * @author yangyunjiao
 */
@Data
@ConfigurationProperties(prefix = PropertyNameConsts.PROPERTY_PREFIX_ID_UIDGENERATOR)
public class UidGeneratorProperties {
    private Integer timeBits = 29;

    private Integer workerBits = 21;

    private Integer seqBits = 13;

    private String epochStr = "2025-08-20";
}
