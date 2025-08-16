package io.yunjiao.spring.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.StreamSupport;

/**
 * 查询条件
 *
 * @author yangyunjiao
 */
public interface QSpecification extends Serializable {

    /**
     * 非条件，如：!(spec)
     * @param spec 可以空
     * @return 新的实例，可能空
     */
    static QSpecification not(@Nullable QSpecification spec) {
        return spec == null
                ? (builder) -> null
                : (builder) -> spec.toPredicate(builder).not();
    }

    /**
     * 第一个条件。如果参数是null, 相当于: where 1=1
     *
     * @param spec 可以空
     * @return 新的实例，可能空
     */
    static QSpecification where(@Nullable QSpecification spec) {
        return SpecificationComposition.composed(spec, null, SpecificationComposition::and);
    }

    /**
     * 并集。如: this && arg
     *
     * @param other 可以空
     * @return 新的实例，可能空
     */
    default QSpecification and(@Nullable QSpecification other) {
        return SpecificationComposition.composed(this, other, SpecificationComposition::and);
    }

    /**
     * 多个并集，如：this && arg1 && arg2 && ...
     *
     * @param QSpecifications 列表， 可以空
     * @return 新的实例，可能空
     */
    default QSpecification and(@Nullable QSpecification... QSpecifications) {
        return and(Arrays.asList(QSpecifications));
    }

    /**
     * 多个并集，参考{@link #and(QSpecification...)}
     *
     * @param specifications 列表， 可以空
     * @return 新的实例，可能空
     */
    default QSpecification and(@Nullable Iterable<QSpecification> specifications) {
        return StreamSupport.stream(specifications.spliterator(), false)
                .reduce(this, QSpecification::and);
    }

    /**
     * 并集 + 非集。如： this && !arg
     *
     * @param other 可以空
     * @return 新的实例，可能空
     */
    default QSpecification andNot(@Nullable QSpecification other) {
        return SpecificationComposition.composed(this, QSpecification.not(other), SpecificationComposition::and);
    }

    /**
     * 并集 + 非集(多个)， 如： this && !arg1 && !arg2 ...
     *
     * @param QSpecifications 列表， 可以空
     * @return 新的实例，可能空
     */
    default QSpecification andNot(@Nullable QSpecification... QSpecifications) {
        return andNot(Arrays.asList(QSpecifications));
    }

    /**
     * 并集 + 非集(多个)， 参考：{@link #and(QSpecification...)}
     *
     * @param specifications 列表， 可以空
     * @return 新的实例，可能空
     */
    default QSpecification andNot(@Nullable Iterable<QSpecification> specifications) {
        return StreamSupport.stream(specifications.spliterator(), false)
                .map(QSpecification::not)
                .reduce(this, QSpecification::and);
    }

    /**
     * 并集 + 或集(多个), 实现如：this && (arg1 || arg2 || arg3 ...)
     * @param QSpecifications 列表， 可以空
     * @return 新的实例，可能空
     */
    default QSpecification andAnyOf(@Nullable QSpecification... QSpecifications) {
        return andAnyOf(Arrays.asList(QSpecifications));
    }

    /**
     * 并集 + 或集(多个), 参考{@link #andAnyOf(QSpecification...)}
     *
     * @param specifications 列表， 可以空
     * @return 新的实例，可能空
     */
    default QSpecification andAnyOf(@Nullable Iterable<QSpecification> specifications) {
        QSpecification spec= StreamSupport.stream(specifications.spliterator(), false)
                .reduce(where(null), QSpecification::or);
        return SpecificationComposition.composed(this, spec, SpecificationComposition::and);
    }

    /**
     * 或集 + 并集(多个)，如：this || (arg1 && arg2 && ...
     * @param QSpecifications 列表， 可以空
     * @return 新的实例，可能空
     */
    default QSpecification orAllOf(@Nullable QSpecification... QSpecifications) {
        return orAllOf(Arrays.asList(QSpecifications));
    }

    /**
     * 或集 + 并集(多个)，参考{@link #orAllOf(QSpecification...)}
     * @param specifications 列表， 可以空
     * @return 新的实例，可能空
     */
    default QSpecification orAllOf(@Nullable Iterable<QSpecification> specifications) {
        QSpecification spec= StreamSupport.stream(specifications.spliterator(), false)
                .reduce(where(null), QSpecification::and);
        return SpecificationComposition.composed(this, spec, SpecificationComposition::or);
    }

    /**
     * 或集，如: this || arg1
     *
     * @param other 可以空
     * @return 新的实例，可能空
     */
    default QSpecification or(@Nullable QSpecification other) {
        return SpecificationComposition.composed(this, other, SpecificationComposition::or);
    }

    /**
     * 或集（多个），如： this || arg1 || arg2 ....
     *
     * @param QSpecifications 列表， 可以空
     * @return 新的实例，可能空
     */
    default QSpecification or(@Nullable QSpecification... QSpecifications) {
        return or(Arrays.asList(QSpecifications));
    }

    /**
     * 或集（多个）, 参考{@link #or(QSpecification...)}
     *
     * @param specifications 列表， 可以空
     * @return 新的实例，可能空
     */
    default QSpecification or(@Nullable Iterable<QSpecification> specifications) {
        return StreamSupport.stream(specifications.spliterator(), false)
                .reduce(this, QSpecification::or);
    }

    /**
     * 或集 + 非集，如： this || !arg
     * @param other 可以空
     * @return 新的实例，可能空
     */
    default QSpecification orNot(@Nullable QSpecification other) {
        return SpecificationComposition.composed(this, QSpecification.not(other), SpecificationComposition::or);
    }

    /**
     * 或集 + 非集(多个)，如： this || !arg1 || !arg2 ...
     *
     * @param QSpecifications 列表， 可以空
     * @return 新的实例，可能空
     */
    default QSpecification orNot(@Nullable QSpecification... QSpecifications) {
        return orNot(Arrays.asList(QSpecifications));
    }

    /**
     * 或集 + 非集(多个)， 参考{@link #orNot(QSpecification...)}
     * @param specifications 列表， 可以空
     * @return 新的实例，可能空
     */
    default QSpecification orNot(@Nullable Iterable<QSpecification> specifications) {
        return StreamSupport.stream(specifications.spliterator(), false)
                .map(QSpecification::not)
                .reduce(this, QSpecification::or);
    }

    @Nullable
    Predicate toPredicate(BooleanBuilder builder);

}
