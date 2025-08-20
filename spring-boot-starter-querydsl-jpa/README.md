# spring-boot-starter-querydsl-jpa

[QueryDSL](http://querydsl.com/) 是一个框架，可以构建静态类型的 SQL 类查询。无需将查询编写为内联字符串或将它们外部化为 XML 文件，
它们可以通过 `Querydsl` 之类的流畅 API 构建。

本项目集成`QueryDSL JPA`框架，实现`JPAQueryFactory`实例自动配置，也可以自由的自定义配置。提供`JPAQueryRepositorySupport`抽象类，
让CURD，分页查询更方便。


## 使用指南

本指南使用`maven`构建工具

### 创建新项目

创建一个新项目，在pom.xml中定义：

```text
......

    <parent>
        <groupId>io.gitee.yunjiao-source</groupId>
        <artifactId>spring-boot-examples</artifactId>
        <version>${你需要的版本}</version>
    </parent>
    
......

    <dependencies>
        <dependency>
            <groupId>io.gitee.yunjiao-source</groupId>
            <artifactId>spring-boot-starter-querydsl-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>com.querydsl</groupId>
            <artifactId>querydsl-apt</artifactId>
            <classifier>jakarta</classifier>
            <scope>provided</scope>
        </dependency>

    </dependencies>
    
......  
```

其他的依赖包请按需添加

### JPA的实体类

创建用户及订单的实体类，是一对多的关系

`Order.java`

```java
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY) // 推荐懒加载
    @JoinColumn(name = "user_id") // 指定外键列名
    private User user;

    private String status;

    private BigDecimal amount;

    @Column(name="transaction_time")
    private Date transactionTime;
}

```

`User.java`

```java
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    private String email;

    @Column(name="birth_date")
    private Date birthDate;

    @Column(name="created_at")
    private Date createdAt;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "user", //
            cascade = CascadeType.ALL, // 级联所有操作（保存/更新/删除）
            orphanRemoval = true // 删除孤儿数据（子实体与父实体断开时自动删除）
    )
    private List<Order> orders = new ArrayList<>();
}
```

创建完成后，执行编译命令`mvn clean compile`后，会在`target/generated-sources`目录
下生成`QOrder`, `QUser`两个类

### 条件查询

`Spring boot` 框架中提供`Specification`接口，支持复杂查询，条件复用等功能。本框架中也提供`QSpecification`
接口

`UserSpec.java`

```java
public final class UserSpec {
    private static final QUser qUser = QUser.user;

    public static QSpecification idEq(Long id) {
        return builder -> qUser.id.eq(id);
    }

    public static QSpecification nameLike(String name) {
        return builder -> qUser.name.like(name);
    }

    public static QSpecification nameEq(String name) {
        return builder -> qUser.name.eq(name);
    }

    public static QSpecification ageGoe(Integer age) {
        return builder -> qUser.age.goe(age);
    }
    public static QSpecification birthDate(Date birthDate) {
        return builder -> qUser.birthDate.goe(birthDate);
    }

}
```

为了更方便的使用上面的条件，可以创建一个类，生成条件

`UserQuery.java`

```java
Getter
@Setter
@Accessors(fluent = true, chain = true)
public class UserQuery {
    private final QUser qUser = QUser.user;
    private String name;
    private Integer minAge;
    private Boolean orderByCreateAt;
    private Boolean orderByAge;


    public QSpecification buildQSpecification() {
        QSpecification spec = QSpecification.where(null);
        if (StringUtils.hasText(name)) {
            spec = spec.and(nameLike(name));
        }
        if (Objects.nonNull(minAge)) {
            spec = spec.and(ageGoe(minAge));
        }
        return spec;
    }

    public QSort buildQsort() {
        QSort sort = new QSort();
        if (Objects.nonNull(orderByAge)) {
            sort = sort.and(orderBy(orderByAge, qUser.age));
        }
        if (Objects.nonNull(orderByCreateAt)) {
            sort = sort.and(orderBy(orderByCreateAt, qUser.createdAt));
        }
        return sort;
    }
}
```

### 仓库

仓库类需要继承`JPAQueryRepositorySupport`

`UserJPARepository.java`

```java
@Repository
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
```

### 服务

`UserJPAService.java`

```java
@Getter
@Service
@RequiredArgsConstructor
public class UserJPAService {
    private final UserJPARepository userJPARepository;

    @Transactional
    public void insertUserBatch() {
        List<User> userList = IntStream.range(0, 1000).mapToObj(i -> {
            User user = new User();
            user.setName("李四-" + i);
            user.setAge(102);

            return user;
        }).toList();
        userJPARepository.insertUserBatch(userList);
    }

    @Transactional
    public void deleteUserBatch() {
        userJPARepository.deleteUser(101);
    }

    public List<User> findByName(String name) {
        UserQuery query = new UserQuery().name(name)
                .orderByAge(true);
        return userJPARepository.findByQuery(query);
    }

}

```

全部代码完成。详细的，可运行的代码请参考示例项目`spring-boot-examples/spring-boot-example-querydsl-jpa`


