package io.yunjiao.springboot.autoconfigure.querydsl.sql;

import com.querydsl.sql.SQLQueryFactory;

/**
 * {@link SQLQueryFactory} 配置器
 *
 * @author yangyunjiao
 */
@FunctionalInterface
public interface SQLQueryFactoryConfigurer {
    /**
     * 配置
     *
     * @param factory 实例
     */
    void configure(SQLQueryFactory factory);
}
