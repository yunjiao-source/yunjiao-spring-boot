package yunjiao.springboot.autoconfigure.id;

import cc.siyecao.uid.core.impl.CachedUidGenerator;

/**
 * {@link CachedUidGenerator} 属性配置器
 *
 * @author yangyunjiao
 */
@FunctionalInterface
public interface CachedUidGeneratorConfigurer {
    /**
     * 配置
     *
     * @param generator 必须值
     */
    void configure(CachedUidGenerator generator);
}
