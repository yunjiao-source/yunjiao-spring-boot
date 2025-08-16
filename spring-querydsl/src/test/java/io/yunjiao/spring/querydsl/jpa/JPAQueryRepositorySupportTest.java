package io.yunjiao.spring.querydsl.jpa;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.yunjiao.spring.querydsl.QSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * TODO
 *
 * @author yangyunjiao
 */
@SpringBootTest
public class JPAQueryRepositorySupportTest {
    @Autowired
    private DemoJPAQueryRepositorySupport repository;

    @Test
    void loadContent() {
        assertThat(repository).isNotNull();
    }

    @Test
    void givenWrongId_whenFindById_thenEntityNotFoundException() {
        assertThatThrownBy(() ->repository.findById(-1L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void givenCorrectId_whenFindById_thenOK() {
        assertThat(repository.findById(2L)).extracting(User::getName).isEqualTo("Michael Miller");
    }

    @Test
    void giveSingleName_whenFindSingleByName_thenOk() {
        assertThat(repository.findSingleByName("zhangs"))
                .isPresent()
                .hasValueSatisfying(user -> {
                    assertThat(user.getName()).isEqualTo("zhangs");
                });
    }

    @Test
    void giveWrongName_whenFindSingleByName_thenOk() {
        assertThat(repository.findSingleByName("lis")).isEmpty();
    }

    @Test
    void givenMutilName_whenFindSingleByName_thenIncorrectResultSizeDataAccessException() {
        assertThatThrownBy(() ->repository.findSingleByName("David Hernandez")).isInstanceOf(IncorrectResultSizeDataAccessException.class);
    }

    @Test
    void whenCount() {
        assertThat(repository.count("Michael Miller")).isEqualTo(3);
    }

    @Test
    void whenFindList() {
        assertThat(repository.findList("Michael Miller", 23, new Date(0))).hasSize(2);
    }

    @Test
    void whenFindQPage() {
        Page<User> users = repository.findQPage(12);
        assertThat(users.getTotalPages()).isEqualTo(3);
        assertThat(users.getNumberOfElements()).isEqualTo(5);
        assertThat(users.getTotalElements()).isEqualTo(15);
        assertThat(users.getContent())
                .hasSize(5)
                .first()
                .extracting(User::getName)
                .isEqualTo("Jessica Thomas");
    }

    @Test
    void whenFindPage() {
        Page<User> users = repository.findPage(12);
        assertThat(users.getTotalPages()).isEqualTo(3);
        assertThat(users.getNumberOfElements()).isEqualTo(5);
        assertThat(users.getTotalElements()).isEqualTo(15);
        assertThat(users.getContent())
                .hasSize(5)
                .first()
                .extracting(User::getName)
                .isEqualTo("David Hernandez");

    }

    @Test
    void whenFindUserByOrderStatus() {
        List<User> users = repository.findUserByOrderStatus();
        assertThat(users).hasSize(1).first().extracting(User::getName).isEqualTo("Michael Miller");
    }

    @Test
    void whenfindUserAndOrders() {
        Pair<User, List<Order>> uo = repository.findUserAndOrders(5L);
        assertThat(uo).isNotNull();
        assertThat(uo.getFirst()).extracting(User::getName).isEqualTo("Michael Miller");
        assertThat(uo.getSecond()).hasSize(2);
    }

    @Repository
    static class DemoJPAQueryRepositorySupport extends JPAQueryRepositorySupport {
        private final static QUser user = QUser.user;
        private final static QOrder order = QOrder.order;

        public DemoJPAQueryRepositorySupport() {
            super(user);
        }

        // 主键查询
        public User findById(Long id) {
            JPAQuery<User> query = selectFrom(user)
                    .where(user.id.eq(id));
            return getCurdExecutor().findMustOne(query);
        }

        // 统计记录数
        public long count(String name) {
            JPAQuery<Long> query = select(user.count())
                    .from(user);
            return getCurdExecutor().count(query, nameEq(name));
        }

        // 使用名称查询唯一用户
        public Optional<User> findSingleByName(String name) {
            JPAQuery<User> query = selectFrom(user);
            return getCurdExecutor().findOnlyOne(query, nameEq(name));
        }

        // 多条件,排序查询
        public List<User> findList(String name, Integer age, Date birthDate) {
            JPAQuery<User> query = selectFrom(user);
            QSpecification spec = nameLike(name).and(ageGoe(age), birthDate(birthDate));
            QSort sort = QSort.by(user.age.asc(), user.birthDate.desc());
            return getCurdExecutor().findList(query, spec, sort);
        }

        // 分页查询, 使用QPageRequest对象
        public Page<User> findQPage(Integer age) {
            JPAQuery<User> query = selectFrom(user);
            QSort sort = QSort.by(user.age.desc());
            return findPage(query, QPageRequest.of(0, 5, sort), ageGoe(age));
        }

        // 分页查询, 使用PageRequest对象
        public Page<User> findPage(Integer age) {
            JPAQuery<User> query = selectFrom(user);
            QSort sort = QSort.by(user.age.asc());
            return findPage(query, PageRequest.of(0, 5, sort), ageGoe(age));
        }

        // 关联查询：查询已完成订单的用户信息
        public List<User> findUserByOrderStatus() {
            JPAQuery<User> query =selectFrom(user)
                    .distinct()
                    .innerJoin(user.orders, order)
                    .where(order.status.eq("completed"));
            return getCurdExecutor().findList(query);
        }

        // 关联查询：获取用户及订单(主从表)
        public Pair<User, List<Order>> findUserAndOrders(Long userId) {
            User user = findById(userId);
            if (Objects.isNull(user)) {
                return null;
            }

            JPAQuery<Order> orderQuery = selectFrom(order)
                    .where(order.user.id.eq(userId));
            QSort sort = QSort.by(order.transactionTime.asc());
            List<Order> orders = getCurdExecutor().findList(orderQuery, sort);
            return Pair.of(user, orders);
        }

        private QSpecification nameEq(String name) {
            return builder -> user.name.eq(name);
        }

        private QSpecification nameLike(String name) {
            return builder -> user.name.like(name);
        }

        private QSpecification ageGoe(Integer age) {
            return builder -> user.age.goe(age);
        }
        private QSpecification birthDate(Date birthDate) {
            return builder -> user.birthDate.goe(birthDate);
        }

    }

    @Configuration
    @EnableTransactionManagement
    public static class TestConfiguration {

        @Bean
        public EntityManager entityManager(EntityManagerFactory emf) {
            return emf.createEntityManager();
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
            LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(dataSource());
            em.setPackagesToScan("io.yunjiao.spring.querydsl.jpa");
            em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

            Properties properties = new Properties();
            properties.setProperty("hibernate.hbm2ddl.auto", "none");
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            properties.setProperty("hibernate.show_sql", "true");
            properties.setProperty("hibernate.format_sql", "false");

            em.setJpaProperties(properties);
            return em;
        }

        @Bean
        public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
            JpaTransactionManager transactionManager = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(entityManagerFactory);
            return transactionManager;
        }

        @Bean
        DemoJPAQueryRepositorySupport demoJPAQueryRepositorySupport() {
            return new DemoJPAQueryRepositorySupport();
        }

        @Bean
        JPAQueryCurdExecutor sqlQueryExecutor() {
            return new JPAQueryCurdExecutor();
        }

        @Bean
        JPAQueryFactory sqlQueryFactory(EntityManager entityManager) {
            return new JPAQueryFactory(entityManager);
        }

        @Bean
        DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.h2.Driver");
            dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL;DATABASE_TO_UPPER=FALSE");
            return dataSource;
        }


        @Bean
        DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
            DataSourceInitializer initializer = new DataSourceInitializer();
            initializer.setDataSource(dataSource);

            // 创建脚本执行器
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(new ClassPathResource("db/h2.sql"));
            populator.setSeparator(";"); // 语句分隔符（可选）

            initializer.setDatabasePopulator(populator);
            initializer.setEnabled(true); // 启用初始化

            return initializer;
        }
    }
}
