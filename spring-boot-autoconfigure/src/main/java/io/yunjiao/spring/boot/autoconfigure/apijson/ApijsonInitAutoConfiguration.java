package io.yunjiao.spring.boot.autoconfigure.apijson;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * APIJSON初始化自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@RequiredArgsConstructor
@AutoConfiguration
public class ApijsonInitAutoConfiguration {
    @PostConstruct
    public void postConstruct() {
        log.info("Apijson Init AutoConfiguration");
    }


    /**
     * {@link ApijsonInitializingBean} 初始化
     *
     * @param parserProperties 解析器配置
     * @param verifierProperties 校验器配置
     * @param sqlProperties sql配置
     * @param sqlConfigConfigurers sql配置器用户配置
     * @param apijsonVerifierConfigurers 校验器用户配置
     * @param apijsonFunctionParserConfigurers 远程函数解析器用户配置
     * @return 实例
     * @see ApijsonInitializingBean
     */
    @Bean
    ApijsonInitializingBean apijsonInitializingBean(ApijsonParserProperties parserProperties,
                                                    ApijsonVerifierProperties verifierProperties,
                                                    ApijsonSqlProperties sqlProperties,
                                                    ObjectProvider<ApijsonSqlConfigConfigurer> sqlConfigConfigurers,
                                                    ObjectProvider<ApijsonVerifierConfigurer> apijsonVerifierConfigurers,
                                                    ObjectProvider<ApijsonFunctionParserConfigurer> apijsonFunctionParserConfigurers) {
        ApijsonInitializingBean bean = new ApijsonInitializingBean(parserProperties, verifierProperties,
                sqlProperties, sqlConfigConfigurers,apijsonVerifierConfigurers,apijsonFunctionParserConfigurers);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Apijson Initializing Bean]");
        }
        return bean;
    }


}
