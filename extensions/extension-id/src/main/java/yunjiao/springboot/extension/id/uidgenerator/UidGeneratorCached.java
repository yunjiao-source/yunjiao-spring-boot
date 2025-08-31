package yunjiao.springboot.extension.id.uidgenerator;

import cc.siyecao.uid.core.impl.CachedUidGenerator;
import cc.siyecao.uid.core.impl.DefaultUidGenerator;

/**
 * {@link DefaultUidGenerator} 包装类，方便注入
 *
 * @author yangyunjiao
 */
public record UidGeneratorCached(CachedUidGenerator cachedUidGenerator) {
    /**
     * 获取ID, 参考{@link CachedUidGenerator#getUID()}
     *
     * @return id
     */
    public long getUID() {
        return cachedUidGenerator.getUID();
    }

    /**
     * 解析ID信息, 参考{@link CachedUidGenerator#parseUID(long)}
     *
     * @param uid id
     * @return 解析信息
     */
    public String parseUID(long uid) {
        return cachedUidGenerator.parseUID(uid);
    }
}
