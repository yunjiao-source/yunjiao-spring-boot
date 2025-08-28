package yunjiao.springboot.autoconfigure.id;

import yunjiao.springboot.extension.id._Id;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Import;

/**
 * 验证码 自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass({_Id.class})
@Import({
        HutoolIdConfiguration.class,
        UidGeneratorConfiguration.class
})
public class IdAutoConfiguration {
    /**
     * {@link PostConstruct} 注解方法
     */
    @PostConstruct
    public void postConstruct() {
        log.info("Id Auto Configuration");
    }
}
