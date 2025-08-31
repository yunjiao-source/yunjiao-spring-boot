package yunjiao.springboot.example.apijson;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yunjiao.springboot.autoconfigure.apijson.MultipleDataSource;
import yunjiao.springboot.autoconfigure.apijson.ApijsonSqlProperties;
import yunjiao.springboot.example.apijson.fastjson2.CustomFastjson2Creator;
import yunjiao.springboot.extension.apjson.orm.DataSourceContextHolder;
import yunjiao.springboot.extension.apjson.util.ApijsonConsts;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@Configuration
public class ApijsonMultipleDataSourceConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource db1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource multipleDataSource(@Qualifier("masterDataSource") DataSource master,
                                        @Qualifier("db1DataSource") DataSource db1) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceContextHolder.DEFAULT, master);
        targetDataSources.put("db1", db1);

        MultipleDataSource multipleDataSource = new MultipleDataSource();
        multipleDataSource.setDefaultTargetDataSource(master); // 设置默认数据源
        multipleDataSource.setTargetDataSources(targetDataSources); // 设置目标数据源Map
        multipleDataSource.afterPropertiesSet(); // 初始化
        return multipleDataSource;
    }

    @Bean
    public CustomFastjson2Creator customFastjson2Creator(@Qualifier("multipleDataSource") DataSource dataSource,
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
