package io.yunjiao.spring.querydsl.sql;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.sql.RelationalPath;
import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.dml.SQLDeleteClause;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLMergeClause;
import com.querydsl.sql.dml.SQLUpdateClause;
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
 * 抽象类，使用QueryDSL SQL实现的仓库
 *
 * @author yangyunjiao
 */
@Repository
public abstract class SQLQueryRepositorySupport {
    private final PathBuilder<?> pathBuilder;

    private @Nullable SQLQueryFactory queryfactory;

    private @Nullable SQLQueryCurdExecutor curdExecutor;


    /**
     * 构造函数，子类的构造函数必须调用
     * @param qbean 必须值
     */
    public SQLQueryRepositorySupport(RelationalPathBase<?> qbean) {
        Assert.notNull(qbean, "QBean class must not be null");
        this.pathBuilder = new PathBuilder<>(qbean.getType(), qbean.getMetadata());
    }

    @Autowired
    public void setQueryfactory(SQLQueryFactory queryFactory) {
        Assert.notNull(queryFactory, "SQLQueryFactory must not be null");
        this.queryfactory = queryFactory;
    }

    @Autowired
    public void setCurdExecutor(SQLQueryCurdExecutor curdExecutor) {
        Assert.notNull(curdExecutor, "SQLQueryCurdExecutor must not be null");
        this.curdExecutor = curdExecutor;
    }

    @PostConstruct
    public void validate() {
        Assert.notNull(queryfactory, "SQLQueryFactory must not be null");
        Assert.notNull(curdExecutor, "SQLQueryCurdExecutor must not be null");
    }

    @Nullable
    protected SQLQueryFactory getQueryFactory() {
        return queryfactory;
    }

    @Nullable
    protected SQLQueryCurdExecutor getCurdExecutor() {
        return curdExecutor;
    }

    /**
     * 创建{@link SQLQuery}实例
     * @return 实例
     */
    protected SQLQuery<?> createQuery() {
        return getRequiredQueryFactory().query();
    }

    /**
     * 查询操作
     *
     * @param exp 必须值
     * @return 实例
     * @param <T> 类型
     */
    protected <T> SQLQuery<T> select(Expression<T> exp) {
        return getRequiredQueryFactory().select(exp);
    }

    /**
     * 查询指定字段
     *
     * @param exprs 必须值
     * @return 实例
     */
    protected SQLQuery<Tuple> select(Expression<?>... exprs) {
        return getRequiredQueryFactory().select(exprs);
    }

    /**
     * 插入操作
     *
     * @param path 必须值
     * @return 实例
     */
    protected SQLInsertClause insert(RelationalPath<?> path) {
        return getRequiredQueryFactory().insert(path);
    }

    /**
     * 批量插入操作
     *
     * @param path 必须值
     * @return 实例
     */
    protected SQLInsertClause insertBatch(RelationalPath<?> path) {
        SQLInsertClause insert = getRequiredQueryFactory().insert(path);
        insert.setBatchToBulk(true);
        return insert;
    }

    /**
     * 更新操作
     *
     * @param path 必须值
     * @return 实例
     */
    protected SQLUpdateClause update(RelationalPath<?> path) {
        return getRequiredQueryFactory().update(path);
    }

    /**
     * 合并操作
     *
     * @param path 必须值
     * @return 实例
     */
    protected SQLMergeClause merge(RelationalPath<?> path) {
        return getRequiredQueryFactory().merge(path);
    }

    /**
     * 删除操作
     *
     * @param path 必须值
     * @return 实例
     */
    protected SQLDeleteClause delete(RelationalPath<?> path) {
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
    protected  <T> Page<T> findPage(SQLQuery<T> query, Pageable pageable) {
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
    protected <T> Page<T> findPage(SQLQuery<T> query, Pageable pageable, @Nullable QSpecification spec) {
        Assert.notNull(query, "SQLQuery must not be null");
        Assert.notNull(pageable, "Pageable must not be null");

        Sort sort = pageable.getSort();
        if (sort.isUnsorted()) {
            return getCurdExecutor().findPage(query, QPageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), spec);
        }

        OrderSpecifier<?>[] osList = QueryDSLUtils.toOrderSpecifiers(sort, pathBuilder);
        return getCurdExecutor().findPage(query, QPageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), osList), spec);
    }

    private SQLQueryFactory getRequiredQueryFactory() {
        if (queryfactory == null) {
            throw new IllegalStateException("SQLQueryFactory is null");
        }

        return queryfactory;
    }
}
