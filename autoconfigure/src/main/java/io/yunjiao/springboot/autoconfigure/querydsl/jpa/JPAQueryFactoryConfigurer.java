package io.yunjiao.springboot.autoconfigure.querydsl.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * {@link JPAQueryFactory} 配置器
 *
 * @author yangyunjiao
 */
@FunctionalInterface
public interface JPAQueryFactoryConfigurer {
    /**
     * 配置
     *
     * @param factory 实例
     */
    void configure(JPAQueryFactory factory);
}
