package io.yunjiao.extension.querydsl.jpa;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.NonUniqueResultException;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import io.yunjiao.extension.querydsl.QSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * {@link JPAQuery} CURD执行器。在所有操作上添加了数据库事务
 *
 * @author yangyunjiao
 */
public class JPAQueryCurdExecutor {
    private static final String JPAQUERY_MUST_NOT_BE_NULL = "JPAQuery must not be null";

    private static final String SPECIFICATION_MUST_NOT_BE_NULL = "Specification must not be null";

    private static final String QPAGEREUEST_MUST_NOT_BE_NULL = "QPageRequest must not be null";

    private static final String QSORT_MUST_NOT_BE_NULL = "QSort must not be null";

    /**
     * 将条件信息应用的更新对象上
     *
     * @param spec 条件信息
     * @param update 更新对象
     * @return 实例
     */
    public JPAQueryCurdExecutor applaySpecification(QSpecification spec, JPAUpdateClause update) {
        Assert.notNull(update, "JPAUpdateClause must not be null");
        if (Objects.isNull(spec)) {
            return this;
        }

        update.where(spec.toPredicate(new BooleanBuilder()));
        return this;
    }

    /**
     * 将条件信息应用的删除对象上
     * @param spec 条件信息
     * @param delete 删除对象
     * @return 实例
     */
    public JPAQueryCurdExecutor applaySpecification(QSpecification spec, JPADeleteClause delete) {
        Assert.notNull(delete, "JPADeleteClause must not be null");
        if (Objects.isNull(spec)) {
            return this;
        }

        delete.where(spec.toPredicate(new BooleanBuilder()));
        return this;
    }

    /**
     * 将条件信息应用的查询对象上
     *
     * @param spec 条件信息
     * @param query 查询对象
     * @return 实例
     * @param <T> 类型
     */
    public <T> JPAQueryCurdExecutor applaySpecification(QSpecification spec, JPAQuery<T> query) {
        Assert.notNull(query, JPAQUERY_MUST_NOT_BE_NULL);
        if (Objects.isNull(spec)) {
            return this;
        }

        Predicate predicate = spec.toPredicate(new BooleanBuilder());
        query.where(predicate);
        return this;
    }

    /**
     * 将排序信息应用的查询对象上
     *
     * @param sort 排序信息
     * @param query 查询对象
     * @return 实例
     * @param <T> 类型
     */
    public <T> JPAQueryCurdExecutor applayQSort(QSort sort, JPAQuery<T> query) {
        Assert.notNull(query, JPAQUERY_MUST_NOT_BE_NULL);

        if (Objects.isNull(sort) || sort.isUnsorted()) {
            return this;
        }

        List<OrderSpecifier<?>> orderSpecifiers = sort.getOrderSpecifiers();
        query.orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]));
        return this;
    }

    /**
     * 将分页信息应用的查询对象上
     *
     * @param pageable 分页信息
     * @param query 查询对象
     * @return 实例
     * @param <T> 类型
     */
    public <T> JPAQueryCurdExecutor applayQPageRequest(QPageRequest pageable, JPAQuery<T> query) {
        Assert.notNull(query, JPAQUERY_MUST_NOT_BE_NULL);
        if (Objects.isNull(pageable)) {
            return this;
        }

        query.offset(pageable.getOffset()).limit(pageable.getPageSize());
        QSort sort = (QSort)pageable.getSort();
        applayQSort(sort, query);

        return this;

    }

    /**
     * 查询仅一个值，参考{@link #findOnlyOne(JPAQuery, QSpecification, QSort)}
     *
     * @param query 查询对象
     * @return 实例
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> Optional<T> findOnlyOne(JPAQuery<T> query) {
        return findOnlyOne(query, null, null);
    }

    /**
     * 查询仅一个值，参考{@link #findOnlyOne(JPAQuery, QSpecification, QSort)}
     *
     * @param query 查询对象
     * @param spec 条件信息
     * @return 实例
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> Optional<T> findOnlyOne(JPAQuery<T> query, QSpecification spec) {
        Assert.notNull(spec, SPECIFICATION_MUST_NOT_BE_NULL);

        return findOnlyOne(query, spec, null);
    }

    /**
     * 查询仅一个值，参考{@link #findOnlyOne(JPAQuery, QSpecification, QSort)}
     *
     * @param query 查询对象
     * @param sort 排序信息
     * @return 实例
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> Optional<T> findOnlyOne(JPAQuery<T> query, QSort sort) {
        Assert.notNull(sort, QSORT_MUST_NOT_BE_NULL);

        return findOnlyOne(query, null, sort);
    }

    /**
     * 查询仅一个值，如果存在多个，就会抛出异常{@link IncorrectResultSizeDataAccessException}
     *
     * @param query 查询对象
     * @param spec 条件信息
     * @param sort 排序信息
     * @return 实例
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> Optional<T> findOnlyOne(JPAQuery<T> query, @Nullable QSpecification spec, @Nullable QSort sort) {
        applaySpecification(spec, query).applayQSort(sort, query);
        try {
            return Optional.ofNullable(query.limit(2).fetchOne());
        } catch (NonUniqueResultException ex) {
            throw new IncorrectResultSizeDataAccessException(ex.getMessage(), 1, ex);
        }
    }

    /**
     * 查询第一个值，{@link #findFirstOne(JPAQuery, QSpecification, QSort)}
     *
     * @param query 查询对象
     * @return 实例
     * @param <T> 类型
     */

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> Optional<T> findFirstOne(JPAQuery<T> query) {
        return findFirstOne(query, null, null);
    }

    /**
     * 查询第一个值，{@link #findFirstOne(JPAQuery, QSpecification, QSort)}
     *
     * @param query 查询对象
     * @param spec 条件信息
     * @return 实例
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> Optional<T> findFirstOne(JPAQuery<T> query, QSpecification spec) {
        Assert.notNull(spec, SPECIFICATION_MUST_NOT_BE_NULL);
        return findFirstOne(query, spec, null);
    }

    /**
     * 查询第一个值，{@link #findFirstOne(JPAQuery, QSpecification, QSort)}
     *
     * @param query 查询对象
     * @param sort 排序信息
     * @return 实例
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> Optional<T> findFirstOne(JPAQuery<T> query, QSort sort) {
        Assert.notNull(sort, QSORT_MUST_NOT_BE_NULL);
        return findFirstOne(query, null, sort);
    }

    /**
     * 查询第一个值，如果存在多个，取第一条
     *
     * @param query 查询对象
     * @param spec 条件信息
     * @param sort 排序信息
     * @return 实例
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> Optional<T> findFirstOne(JPAQuery<T> query, @Nullable QSpecification spec, @Nullable QSort sort) {
        Assert.notNull(query, JPAQUERY_MUST_NOT_BE_NULL);
        applaySpecification(spec, query).applayQSort(sort, query);
        return Optional.ofNullable(query.limit(1).fetchOne());
    }

    /**
     * 查询必须的一条，参考{@link #findMustOne(JPAQuery, QSpecification, QSort)}
     *
     * @param query 查询对象
     * @return 实例
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> T findMustOne(JPAQuery<T> query) {
        return findMustOne(query, null, null);
    }

    /**
     * 查询必须的一条，参考{@link #findMustOne(JPAQuery, QSpecification, QSort)}
     *
     * @param query 查询对象
     * @return 实例
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> T findMustOne(JPAQuery<T> query, QSpecification spec) {
        Assert.notNull(spec, SPECIFICATION_MUST_NOT_BE_NULL);
        return findMustOne(query, spec, null);
    }

    /**
     * 查询必须的一条，参考{@link #findMustOne(JPAQuery, QSpecification, QSort)}
     *
     * @param query 查询对象
     * @param sort 排序信息
     * @return 实例
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> T findMustOne(JPAQuery<T> query, QSort sort) {
        Assert.notNull(sort, QSORT_MUST_NOT_BE_NULL);
        return findMustOne(query, null, sort);
    }

    /**
     * 查询必须的一条。 如果有多条，取第一条；如果没有，就抛出异常{@link EntityNotFoundException}
     *
     * @param query 查询对象
     * @param spec 条件信息
     * @param sort 排序信息
     * @return 实例
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> T findMustOne(JPAQuery<T> query, @Nullable QSpecification spec, @Nullable QSort sort) {
        Assert.notNull(query, JPAQUERY_MUST_NOT_BE_NULL);

        applaySpecification(spec, query).applayQSort(sort, query);
        return Optional.ofNullable(query.limit(1).fetchOne()).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * 统计记录数
     *
     * @param query 查询对象
     * @param spec 条件信息
     * @return 记录数量
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public long count(JPAQuery<Long> query, QSpecification spec) {
        Assert.notNull(query, JPAQUERY_MUST_NOT_BE_NULL);
        Assert.notNull(spec, SPECIFICATION_MUST_NOT_BE_NULL);
        applaySpecification(spec, query);
        return Optional.ofNullable(query.limit(1).fetchOne()).orElse(0L);
    }

    /**
     * 记录是否存在。存在返回true；反之false
     *
     * @param query 查询对象
     * @param spec 条件信息
     * @return 存在返回true；反之false
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> boolean exist(JPAQuery<T> query, QSpecification spec) {
        return findFirstOne(query, spec).isPresent();
    }

    /**
     * 查询列表
     *
     * @param query 查询对象
     * @return 列表
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> List<T> findList(JPAQuery<T> query) {
        return findList(query, null, null);
    }

    /**
     * 查询列表
     *
     * @param query 查询对象
     * @param spec 条件对象
     * @return 列表
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> List<T> findList(JPAQuery<T> query, QSpecification spec) {
        Assert.notNull(spec, SPECIFICATION_MUST_NOT_BE_NULL);

        return findList(query, spec, null);
    }

    /**
     * 查询列表
     *
     * @param query 查询对象
     * @param sort 排序对象
     * @return 列表
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> List<T> findList(JPAQuery<T> query, QSort sort) {
        Assert.notNull(sort, QSORT_MUST_NOT_BE_NULL);

        return findList(query, null, sort);
    }

    /**
     * 查询列表
     *
     * @param query 查询对象
     * @param spec 条件对象
     * @param sort 排序对象
     * @return 列表
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> List<T> findList(JPAQuery<T> query, @Nullable QSpecification spec, @Nullable QSort sort) {
        Assert.notNull(query, JPAQUERY_MUST_NOT_BE_NULL);

        applaySpecification(spec, query).applayQSort(sort, query);
        return query.fetch();
    }

    /**
     * 查询列表
     *
     * @param query 查询对象
     * @return 列表
     */
    @Transactional(readOnly = true)
    public List<Tuple> findTuple(JPAQuery<Tuple> query) {
        return findTuple(query, null, null);
    }

    /**
     * 查询列表
     *
     * @param query 查询对象
     * @param spec 条件对象
     * @return 列表
     */
    @Transactional(readOnly = true)
    public List<Tuple> findTuple(JPAQuery<Tuple> query, @Nullable QSpecification spec) {
        Assert.notNull(spec, SPECIFICATION_MUST_NOT_BE_NULL);

        return findTuple(query, spec, null);
    }


    /**
     * 查询列表
     *
     * @param query 查询对象
     * @param sort 排序对象
     * @return 列表
     */
    @Transactional(readOnly = true)
    public List<Tuple> findTuple(JPAQuery<Tuple> query, @Nullable QSort sort) {
        Assert.notNull(sort, QSORT_MUST_NOT_BE_NULL);

        return findTuple(query, null, sort);
    }

    /**
     * 查询列表
     *
     * @param query 查询对象
     * @param spec 条件对象
     * @param sort 排序对象
     * @return 列表
     */
    @Transactional(readOnly = true)
    public List<Tuple> findTuple(JPAQuery<Tuple> query, @Nullable QSpecification spec, @Nullable QSort sort) {
        Assert.notNull(query, JPAQUERY_MUST_NOT_BE_NULL);

        applaySpecification(spec, query).applayQSort(sort, query);
        return query.fetch();
    }


    /**
     * 查询分页
     *
     * @param query 查询对象
     * @param pageable 分页信息
     * @return 页信息
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> Page<T> findPage(JPAQuery<Long> countQuery, JPAQuery<T> query, QPageRequest pageable) {
        Assert.notNull(pageable, QPAGEREUEST_MUST_NOT_BE_NULL);
        return findPage(countQuery, query, pageable, null);
    }

    /**
     * 查询分页
     *
     * @param query 查询对象
     * @param pageable 分页信息
     * @param spec 条件信息
     * @return 页信息
     * @param <T> 类型
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public <T> Page<T> findPage(JPAQuery<Long> countQuery, JPAQuery<T> query, QPageRequest pageable, @Nullable QSpecification spec) {
        Assert.notNull(countQuery, "countQuery must not be null");
        Assert.notNull(query, JPAQUERY_MUST_NOT_BE_NULL);

        // 获取总数
        long total = count(countQuery, spec);
        if (total <= 0) {
            return new PageImpl<>(Collections.emptyList(), pageable, total);
        }

        applayQPageRequest(pageable, query);
        List<T> content = query.fetch();
        return new PageImpl<>(content, pageable, total);
    }

    /**
     * 更新
     *
     * @param update 更新对象
     * @param spec 条件信息
     * @return 更新影响记录数
     */
    @Transactional(rollbackFor = Exception.class)
    public long update(JPAUpdateClause update, QSpecification spec) {
        Assert.notNull(update, "JPAUpdateClause must not be null");
        Assert.notNull(spec, SPECIFICATION_MUST_NOT_BE_NULL);

        update.where(spec.toPredicate(new BooleanBuilder()));
        return update.execute();
    }

    /**
     * 删除
     *
     * @param delete 删除对象
     * @param spec 条件信息
     * @return 删除影响记录数
     */
    @Transactional(rollbackFor = Exception.class)
    public long delete(JPADeleteClause delete, QSpecification spec) {
        Assert.notNull(delete, "JPADeleteClause must not be null");
        Assert.notNull(spec, SPECIFICATION_MUST_NOT_BE_NULL);

        delete.where(spec.toPredicate(new BooleanBuilder()));
        return delete.execute();
    }
}
