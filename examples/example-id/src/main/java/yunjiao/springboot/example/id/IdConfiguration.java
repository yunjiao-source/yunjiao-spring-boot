package yunjiao.springboot.example.id;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yunjiao.springboot.autoconfigure.id.CachedUidGeneratorConfigurer;

/**
 * 配置
 *
 * @author yangyunjiao
 */
@Slf4j
@Configuration
public class IdConfiguration {

    @Bean
    CachedUidGeneratorConfigurer CachedUidGeneratorConfigurer() {
        return generator -> {
            log.info("自定义配置");
            generator.setBoostPower(3);
            generator.setPaddingFactor(50);
            generator.setScheduleInterval(60);
        };
    }

}
