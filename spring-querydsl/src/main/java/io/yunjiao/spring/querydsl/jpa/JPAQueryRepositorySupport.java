package io.yunjiao.spring.querydsl.jpa;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.*;
import io.yunjiao.spring.querydsl.QSpecification;
import io.yunjiao.spring.querydsl.QueryDSLUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * 抽象类，使用QueryDSL JPA实现的仓库
 *
 * @author yangyunjiao
 */
@Repository
public abstract class JPAQueryRepositorySupport {
    private final PathBuilder<?> pathBuilder;

    private final EntityPathBase<?> qbean;

    private @Nullable JPAQueryFactory queryfactory;

    private @Nullable JPAQueryCurdExecutor curdExecutor;


    /**
     * 构造函数，子类的构造函数必须调用
     * @param qbean 必须值
     */
    public JPAQueryRepositorySupport(EntityPathBase<?> qbean) {
        Assert.notNull(qbean, "QBean class must not be null");
        this.qbean = qbean;
        this.pathBuilder = new PathBuilder<>(qbean.getType(), qbean.getMetadata());
    }

    /**
     * 设置 {@link JPAQueryFactory}
     *
     * @param queryFactory 必须值
     */
    @Autowired
    public void setQueryfactory(JPAQueryFactory queryFactory) {
        Assert.notNull(queryFactory, "JPAQueryFactory must not be null");
        this.queryfactory = queryFactory;
    }

    /**
     * 设置 {@link JPAQueryCurdExecutor}
     *
     * @param curdExecutor 必须值
     */
    @Autowired
    public void setCurdExecutor(JPAQueryCurdExecutor curdExecutor) {
        Assert.notNull(curdExecutor, "JPAQueryCurdExecutor must not be null");
        this.curdExecutor = curdExecutor;
    }

    /**
     * 校验必须属性
     */
    @PostConstruct
    public void validate() {
        Assert.notNull(queryfactory, "JPAQueryFactory must not be null");
        Assert.notNull(curdExecutor, "JPAQueryCurdExecutor must not be null");
    }

    /**
     * 获取 {@link JPAQueryFactory}
     *
     * @return 实例
     */
    @Nullable
    protected JPAQueryFactory getQueryFactory() {
        return queryfactory;
    }

    /**
     * 获取 {@link JPAQueryCurdExecutor}
     *
     * @return 实例
     */
    @Nullable
    protected JPAQueryCurdExecutor getCurdExecutor() {
        return curdExecutor;
    }

    /**
     * 创建{@link JPAQuery}实例
     * @return 实例
     */
    protected JPAQuery<?> createQuery() {
        return getRequiredQueryFactory().query();
    }

    /**
     * 参考 {@link JPAQueryFactory#select(Expression)}
     *
     * @param expr 必须值
     * @return 实例
     * @param <T> 类型
     */
    protected <T> JPAQuery<T> select(Expression<T> expr) {
        return getRequiredQueryFactory().select(expr);
    }

    /**
     * 参考 {@link JPAQueryFactory#select(Expression[])}
     *
     * @param exprs 必须值
     * @return 实例
     */
    protected JPAQuery<Tuple> select(Expression<?>... exprs) {
        return getRequiredQueryFactory().select(exprs);
    }


    /**
     * 参考 {@link JPAQueryFactory#from(EntityPath)}
     *
     * @param from 必须值
     * @return 实例
     */
    protected JPAQuery<?> from(EntityPath<?> from) {
        return getRequiredQueryFactory().from(from);
    }

    /**
     * 参考 {@link JPAQueryFactory#from(EntityPath[])}
     *
     * @param froms 必须值
     * @return 实例
     */
    protected JPAQuery<?> from(EntityPath<?>... froms) {
        return getRequiredQueryFactory().from(froms);
    }

    /**
     * 参考 {@link JPAQueryFactory#selectFrom(EntityPath)}
     *
     * @param from 必须值
     * @return 实例
     * @param <T> 类型
     */
    protected <T> JPAQuery<T> selectFrom(EntityPath<T> from) {
        return getRequiredQueryFactory().selectFrom(from);
    }

    /**
     * 插入操作
     *
     * @param path 必须值
     * @return 实例
     */
    protected JPAInsertClause insert(EntityPath<?> path) {
        return getRequiredQueryFactory().insert(path);
    }

    /**
     * 更新操作
     *
     * @param path 必须值
     * @return 实例
     */
    protected JPAUpdateClause update(EntityPath<?> path) {
        return getRequiredQueryFactory().update(path);
    }

    /**
     * 删除操作
     *
     * @param path 必须值
     * @return 实例
     */
    protected JPADeleteClause delete(EntityPath<?> path) {
        return getRequiredQueryFactory().delete(path);
    }

    /**
     * 分页查询
     *
     * @param query 实例
     * @param pageable 分页信息
     * @return 分页数据
     * @param <T> 类型
     */
    protected  <T> Page<T> findPage(JPAQuery<T> query, Pageable pageable) {
        return findPage(query, pageable, null);
    }

    /**
     * 分页查询
     *
     * @param query 实例
     * @param pageable 分页信息
     * @param spec 条件信息
     * @return 分页数据
     * @param <T> 类型
     */
    protected <T> Page<T> findPage(JPAQuery<T> query, Pageable pageable, @Nullable QSpecification spec) {
        Assert.notNull(query, "JPAQuery must not be null");
        Assert.notNull(pageable, "Pageable must not be null");

        // 统计记录数查询
        JPAQuery<Long> countQuery = query.clone().select(qbean.count());

        Sort sort = pageable.getSort();
        if (sort.isUnsorted()) {
            return getCurdExecutor().findPage(countQuery, query, QPageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), spec);
        }

        OrderSpecifier<?>[] osList = QueryDSLUtils.toOrderSpecifiers(sort, pathBuilder);
        return getCurdExecutor().findPage(countQuery, query, QPageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), osList), spec);
    }

    /**
     * 分页查询
     *
     * @param query 实例
     * @param pageable 分页信息
     * @return 分页数据
     * @param <T> 类型
     */
    protected  <T> Page<T> findPage(JPAQuery<T> query, QPageRequest pageable) {
        return findPage(query, pageable, null);
    }

    /**
     * 分页查询
     *
     * @param query 实例
     * @param pageable 分页信息
     * @param spec 条件信息
     * @return 分页数据
     * @param <T> 类型
     */
    protected <T> Page<T> findPage(JPAQuery<T> query, QPageRequest pageable, @Nullable QSpecification spec) {
        Assert.notNull(query, "JPAQuery must not be null");
        Assert.notNull(pageable, "Pageable must not be null");

        // 统计记录数查询
        JPAQuery<Long> countQuery = query.clone().select(qbean.count());

        Sort sort = pageable.getSort();
        if (sort.isUnsorted()) {
            return getCurdExecutor().findPage(countQuery, query, QPageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), spec);
        }

        OrderSpecifier<?>[] osList = QueryDSLUtils.toOrderSpecifiers(sort, pathBuilder);
        return getCurdExecutor().findPage(countQuery, query, QPageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), osList), spec);
    }

    /**
     * 获取 {@link JPAQueryFactory}，如果是null，则抛出异常
     * @return 实例
     */
    private JPAQueryFactory getRequiredQueryFactory() {
        if (queryfactory == null) {
            throw new IllegalStateException("JPAQueryFactory is null");
        }

        return queryfactory;
    }
}
