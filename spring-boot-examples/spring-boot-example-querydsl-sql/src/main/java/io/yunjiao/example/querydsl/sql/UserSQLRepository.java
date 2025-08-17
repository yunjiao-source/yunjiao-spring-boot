package io.yunjiao.example.querydsl.sql;

import com.google.common.collect.Lists;
import com.querydsl.core.types.Projections;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.dml.SQLDeleteClause;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLUpdateClause;
import io.yunjiao.spring.querydsl.QSpecification;
import io.yunjiao.spring.querydsl.sql.SQLQueryRepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static io.yunjiao.example.querydsl.sql.UserSpec.*;

/**
 * 仓库类
 *
 * @author yangyunjiao
 */
@Repository
@Transactional
public class UserSQLRepository extends SQLQueryRepositorySupport {
    private static final QUsers qUser = QUsers.users;
    private static final QOrders qOrder = QOrders.orders;


    public UserSQLRepository() {
        super(qUser);
    }

    // 主键查询
    public User findById(Long id) {
        SQLQuery<User> query = select(Projections.bean(User.class, qUser.all()))
                .from(qUser)
                .where(qUser.id.eq(id));
        return getCurdExecutor().findMustOne(query);
    }


    // 使用名称查询唯一用户
    public Optional<User> findSingleByName(String name) {
        SQLQuery<User> query = select(Projections.bean(User.class, qUser.all()))
                .from(qUser);
        return getCurdExecutor().findOnlyOne(query, nameEq(name));
    }

    // 多条件,排序查询
    public List<User> findList(String name, Integer age, Date birthDate) {
        SQLQuery<User> query = select(Projections.bean(User.class, qUser.all()))
                .from(qUser);
        QSpecification spec = nameLike(name).and(ageGoe(age), birthDateEq(birthDate));
        QSort sort = QSort.by(qUser.age.asc(), qUser.birthDate.desc());
        return getCurdExecutor().findList(query, spec, sort);
    }

    // 分页查询, 使用QPageRequest对象
    public Page<User> findQPage(Integer age) {
        SQLQuery<User> query = select(Projections.bean(User.class, qUser.all()))
                .from(qUser);
        QSort sort = QSort.by(qUser.age.desc());
        return getCurdExecutor().findPage(query, QPageRequest.of(0, 5, sort), ageGoe(age));
    }

    // 分页查询, 使用PageRequest对象
    public Page<User> findPage(Integer age) {
        SQLQuery<User> query = select(Projections.bean(User.class, qUser.all()))
                .from(qUser);
        QSort sort = QSort.by(qUser.age.asc());
        return findPage(query, PageRequest.of(0, 5, sort), ageGoe(age));
    }

    // 关联查询：查询已完成订单的用户信息
    public List<User> findUserByOrderStatus() {
        SQLQuery<User> query = select(Projections.bean(User.class, qUser.all()))
                .distinct()
                .from(qUser)
                .innerJoin(qUser.order, qOrder)
                .where(qOrder.status.eq("completed"));
        return getCurdExecutor().findList(query);
    }

    // 插入数据示例
    public Long insertUser(User user) {
        return insert(qUser)
                .set(qUser.name, user.getName())
                .set(qUser.age, user.getAge())
                .executeWithKey(qUser.id); // 返回自增ID
    }

    // 更新数据
    public long updateName(Long id, String newName) {
        SQLUpdateClause update = update(qUser)
                .set(qUser.name, newName);
        return getCurdExecutor().update(update, idEq(id));
    }

    // 批量插入
    @Transactional(rollbackFor = Exception.class)
    public void insertUserBatch(List<User> users) {
        List<List<User>> batches = Lists.partition(users, 100);
        batches.forEach(usersSub -> {
            SQLInsertClause insert = insertBatch(qUser);
            for (User user : usersSub) {
                insert.populate(user)
                        .addBatch();
            }

            // 执行批处理并获取影响行数
            long batchResults = insert.execute();
            System.out.println("分批插入完成，影响行数: " + batchResults);
        });
    }

    // 模拟大量删除
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Integer age) {
        final long maxLimit = 99L;

        // 语句
        SQLDeleteClause delete = delete(qUser).limit(maxLimit);
        // 添加条件
        getCurdExecutor().applaySpecification(ageGoe(age), delete);
        while (true) {
            // 执行
            long count = delete.execute();
            System.out.println("分批删除完成，影响行数: " + count);
            if (count < maxLimit) {
                break;
            }
        }

    }

    // 使用查询对象
    public List<User> findByQuery(UserQuery query) {
        return getCurdExecutor().findList(select(Projections.bean(User.class, qUser.all())).from(qUser),
                query.buildQSpecification(),
                query.buildQsort());
    }

    public List<User> findByQuery0(UserQuery query) {
        return getCurdExecutor().findList(select(Projections.bean(User.class, qUser.all())).from(qUser),
                QSpecification.where(query.buildPredicate()),
                query.buildQsort());
    }
}
