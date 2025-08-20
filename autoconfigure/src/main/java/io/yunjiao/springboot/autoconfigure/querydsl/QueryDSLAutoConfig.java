package io.yunjiao.springboot.autoconfigure.querydsl;

import com.querydsl.core.QueryFactory;
import io.yunjiao.springboot.autoconfigure.querydsl.jpa.QuerydslJPAAutoConfiguration;
import io.yunjiao.springboot.autoconfigure.querydsl.sql.QuerydslSQLAutoConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * QueryDSL 自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@AutoConfiguration(after = DataSourceAutoConfiguration.class)
@ConditionalOnClass({QueryFactory.class})
@Import({
        QuerydslJPAAutoConfiguration.class,
        QuerydslSQLAutoConfiguration.class
})
public class QueryDSLAutoConfig {

    /**
     * {@link PostConstruct} 注解方法
     */
    @PostConstruct
    public void postConstruct() {
        log.info("QueryDSL Auto Configuration");
    }


}
