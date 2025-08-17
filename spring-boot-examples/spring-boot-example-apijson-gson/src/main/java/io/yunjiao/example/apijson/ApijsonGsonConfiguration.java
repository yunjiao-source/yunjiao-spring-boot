package io.yunjiao.example.apijson;

import io.yunjiao.example.apijson.gson.CustomGsonCreator;
import io.yunjiao.spring.apijson.util.ApijsonConsts;
import io.yunjiao.spring.boot.autoconfigure.apijson.ApijsonSqlProperties;
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
public class ApijsonGsonConfiguration {
    @Bean
    public CustomGsonCreator customFastjson2Creator(DataSource dataSource,
                                                    ApijsonSqlProperties sqlProperties) {
        log.debug("Configure Bean [Custom Gson Creator]");
        return new CustomGsonCreator(dataSource, sqlProperties);
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
