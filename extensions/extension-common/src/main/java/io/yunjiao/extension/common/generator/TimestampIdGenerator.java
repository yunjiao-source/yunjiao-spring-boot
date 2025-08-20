package io.yunjiao.extension.common.generator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 当前时间戳的ID生成器，Long类型，提供静态方法生成ID。线程安全的，仅用于测试或示例
 *
 * @author yangyunjiao
 */
public final class TimestampIdGenerator {
    private static final AtomicLong counter = new AtomicLong(System.currentTimeMillis() * 1000);

    public static long next() {
        return counter.getAndIncrement();
    }
}
