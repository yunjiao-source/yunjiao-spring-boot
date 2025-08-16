package io.yunjiao.example.querydsl.jpa;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAInsertClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import io.yunjiao.spring.querydsl.QSpecification;
import io.yunjiao.spring.querydsl.jpa.JPAQueryRepositorySupport;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static io.yunjiao.example.querydsl.jpa.UserSpec.*;

/**
 * 仓库类
 *
 * @author yangyunjiao
 */
@Repository
@Transactional
public class UserJPARepository extends JPAQueryRepositorySupport {
    private static final QUser qUser = QUser.user;
    private static final QOrder qOrder = QOrder.order;

    @Autowired
    private EntityManager entityManager;


    public UserJPARepository() {
        super(qUser);
    }

    // 主键查询
    public User findById(Long id) {
        JPAQuery<User> query = selectFrom(qUser)
                .where(qUser.id.eq(id));
        return getCurdExecutor().findMustOne(query);
    }

    public User findOrderById(Long id) {
        JPAQuery<User> query = selectFrom(qUser)
                .leftJoin(qUser.orders, qOrder).fetchJoin()
                .where(qUser.id.eq(id));
        return getCurdExecutor().findMustOne(query);
    }

    // 使用名称查询唯一用户
    public Optional<User> findSingleByName(String name) {
        JPAQuery<User> query = selectFrom(qUser);
        return getCurdExecutor().findOnlyOne(query, nameEq(name));
    }

    // 多条件,排序查询
    public List<User> findList(String name, Integer age, Date birthDate) {
        JPAQuery<User> query = selectFrom(qUser);
        QSpecification spec = nameLike(name).and(ageGoe(age), birthDate(birthDate));
        QSort sort = QSort.by(qUser.age.asc(), qUser.birthDate.desc());
        return getCurdExecutor().findList(query, spec, sort);
    }

    // 分页查询, 使用QPageRequest对象
    public Page<User> findQPage(Integer age) {
        JPAQuery<Long> countQuery = select(qUser.count()).from(qUser);
        JPAQuery<User> query = selectFrom(qUser);
        QSort sort = QSort.by(qUser.age.desc());
        return getCurdExecutor().findPage(countQuery, query, QPageRequest.of(0, 5, sort), ageGoe(age));
    }

    // 分页查询, 使用PageRequest对象
    public Page<User> findPage(Integer age) {
        JPAQuery<User> query = selectFrom(qUser);
        QSort sort = QSort.by(qUser.age.asc());
        return findPage(query, PageRequest.of(0, 5, sort), ageGoe(age));
    }

    // 关联查询：查询已完成订单的用户信息
    public List<User> findUserByOrderStatus() {
        JPAQuery<User> query = selectFrom(qUser)
                .distinct()
                .innerJoin(qUser.orders, qOrder)
                .where(qOrder.status.eq("completed"));
        return getCurdExecutor().findList(query);
    }

    // 插入数据示例
    public long insertUser(User user) {
        return insert(qUser)
                .columns(qUser.name, qUser.age)
                .values(user.getName(), user.getAge())
                .execute();
    }

    // 更新数据
    public long updateName(Long id, String newName) {
        JPAUpdateClause update = update(qUser)
                .set(qUser.name, newName);
        return getCurdExecutor().update(update, idEq(id));
    }

    // 批量插入
    @Transactional(rollbackFor = Exception.class)
    public void insertUserBatch(List<User> users) {
        long count = 0;
        for (User user : users) {
            JPAInsertClause insert = insert(qUser)
                    .columns(qUser.name, qUser.age);
            insert.values(user.getName(), user.getAge());
            count += insert.execute();
        }
        System.out.println("插入行数 " + count);
    }

    // 模拟大量删除
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Integer age) {
        final long maxLimit = 99L;

        // 查询
        JPAQuery<Long> queryClause = select(qUser.id).from(qUser).limit(maxLimit);
        getCurdExecutor().applaySpecification(ageGoe(age), queryClause);

        while (true) {
            List<Long> userIdList = queryClause.fetch();
            if (userIdList.isEmpty()) {
                break;
            }

            // 删除语句
            JPADeleteClause deleteClause = delete(qUser).where(qUser.id.in(userIdList));
            // 执行
            long count = deleteClause.execute();
            System.out.println("分批删除完成，影响行数: " + count);
            if (count < maxLimit) {
                break;
            }
        }

    }

    // 使用查询对象
    public List<User> findByQuery(UserQuery query) {
        return getCurdExecutor().findList(selectFrom(qUser),
                query.buildQSpecification(),
                query.buildQsort());
    }


}
