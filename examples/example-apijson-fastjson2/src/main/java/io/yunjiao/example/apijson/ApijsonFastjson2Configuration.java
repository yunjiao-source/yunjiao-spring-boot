package io.yunjiao.example.apijson;

import io.yunjiao.example.apijson.fastjson2.CustomFastjson2Creator;
import io.yunjiao.extension.apjson.util.ApijsonConsts;
import io.yunjiao.springboot.autoconfigure.apijson.ApijsonSqlProperties;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

/**
 * 自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@Configuration
public class ApijsonFastjson2Configuration {
    @Bean
    public CustomFastjson2Creator customFastjson2Creator(DataSource dataSource,
                                                         ApijsonSqlProperties sqlProperties) {
        log.debug("Configure Bean [Custom Fastjson2 Creator]");
        return new CustomFastjson2Creator(dataSource, sqlProperties);
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@Nonnull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("*")
                        .allowCredentials(true)
                        .exposedHeaders(ApijsonConsts.APIJSON_DELEGATE_ID)  // Cookie 和 Set-Cookie 怎么设置都没用 ,Cookie,Set-Cookie")   // .exposedHeaders("*")
                        .maxAge(3600);
            }
        };
    }
}
