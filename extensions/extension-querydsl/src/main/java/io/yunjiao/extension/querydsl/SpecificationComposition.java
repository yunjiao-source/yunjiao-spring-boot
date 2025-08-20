package io.yunjiao.extension.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Objects;

/**
 * 帮助类，支持{@link QSpecification} 合并
 *
 * @author yangyunjiao
 */
public class SpecificationComposition {
    /**
     * or(合集) 操作
     *
     * @param builder 建造器
     * @param predicate 断言
     */
    public static void or(BooleanBuilder builder, Predicate predicate) {
        builder.or(predicate);
    }

    /**
     * and(并集) 操作
     *
     * @param builder 建造器
     * @param predicate 断言
     */
    public static void and(BooleanBuilder builder, Predicate predicate) {
        builder.and(predicate);
    }

    /**
     * 合并两个条件信息
     *
     * @param lhs 左条件信息
     * @param rhs 右条件信息
     * @param combiner 合并器
     * @return 新的实例
     */
    static QSpecification composed(@Nullable QSpecification lhs, @Nullable QSpecification rhs,
                                   Combiner combiner) {

        return (builder) -> {

            BooleanBuilder newBuilder = new BooleanBuilder();

            Predicate thisPredicate = toPredicate(lhs, builder);
            Predicate otherPredicate = toPredicate(rhs, builder);

            if (Objects.nonNull(thisPredicate)) {
                combiner.combine(newBuilder, thisPredicate);
            }

            if (Objects.nonNull(otherPredicate)) {
                combiner.combine(newBuilder, otherPredicate);
            }

            combiner.combine(builder, newBuilder);
            return newBuilder;
        };
    }

    @Nullable
    private static Predicate toPredicate(@Nullable QSpecification QSpecification, BooleanBuilder builder) {
        return QSpecification == null ? null : QSpecification.toPredicate(builder);
    }

    /**
     * 合并器接口, 有函数实现,分别是 {@link #or(BooleanBuilder, Predicate)}, {@link #and(BooleanBuilder, Predicate)}
     */
    interface Combiner extends Serializable {
        void combine(BooleanBuilder builder, Predicate predicate);
    }
}
