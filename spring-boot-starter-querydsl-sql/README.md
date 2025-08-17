# spring-boot-starter-querydsl-sql

[QueryDSL](http://querydsl.com/) 是一个框架，可以构建静态类型的 SQL 类查询。无需将查询编写为内联字符串或将它们外部化为 XML 文件，
它们可以通过 `Querydsl` 之类的流畅 API 构建。

本项目集成`QueryDSL SQL`框架，实现`SQLQueryFactory`实例自动配置，也可以自由的自定义配置。提供`SQLQueryRepositorySupport`抽象类，
让CURD，分页查询更方便。

## 版本历史

| 起始版本 | 依赖包及版本 | 说明 |
|------|--------|----|
| 1    |   querydsl-sql-5.1.0     ||

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
            <artifactId>spring-boot-starter-querydsl-sql</artifactId>
        </dependency>

        <dependency>
            <groupId>com.querydsl</groupId>
            <artifactId>querydsl-sql-codegen</artifactId>
            <scope>provided</scope>
        </dependency>

    </dependencies>

......

    <build>
        <plugins>
            <plugin>
                <groupId>com.querydsl</groupId>
                <artifactId>querydsl-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>export</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <!-- 注意修改一下信息 -->
                    <jdbcDriver>com.mysql.cj.jdbc.Driver</jdbcDriver>
                    <jdbcUrl>jdbc:mysql://localhost:3306/yunjiao?userSSL=false&amp;serverTimezone=GMT%2B8&amp;useUnicode=true&amp;characterEncoding=UTF-8</jdbcUrl>
                    <jdbcUser>system</jdbcUser>
                    <jdbcPassword>system</jdbcPassword>
                    <packageName>io.yunjiao.example.querydsl</packageName>
                    <targetFolder>${project.basedir}/target/generated-sources/java</targetFolder>
                    <sourceFolder/>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>com.mysql</groupId>
                        <artifactId>mysql-connector-j</artifactId>
                        <version>9.2.0</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
    
......  
```

其他的依赖包请按需添加

### 自定义SQLQueryFactory

创建一个配置Bean
```java
    @Bean
    SQLQueryFactoryConfigurer sqlQueryFactoryConfigurer() {
        return sqlQueryFactory -> {
            com.querydsl.sql.Configuration config = sqlQueryFactory.getConfiguration();
            log.info("配置：Configuration={}", config);
            
            SQLTemplates template = config.getTemplates();
            log.info("配置：SQLTemplates={}", template);
        };
    }
```

### 实体类

创建用户及订单的实体类，是一对多的关系

`User.java`

```java
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String birthData;
    private Date createAt;
}

```

创建完成后，执行编译命令`mvn clean compile`后，会在`target/generated-sources`目录
下生成`QOrder`, `QUser`两个类。

注意：数据库中的所有表都会被创建Q类

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
@Getter
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

仓库类需要继承`SQLQueryRepositorySupport`

`UserSQLRepository.java`

```java
@Repository
@Transactional
public class UserSQLRepository extends SQLQueryRepositorySupport {
    private static final QUser qUser = QUser.user;
    private static final QOrder qOrder = QOrder.order;


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
        QSpecification spec = nameLike(name).and(ageGoe(age), birthDate(birthDate));
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


}
```

### 服务

`UserSQLService.java`

```java
@Getter
@Service
@RequiredArgsConstructor
public class UserSQLService {
    private final UserSQLRepository userSQLRepository;

    @Transactional
    public void insertUserBatch() {
        List<User> userList = IntStream.range(0, 1000).mapToObj(i -> {
            User user = new User();
            user.setName("李四-" + i);
            user.setAge(102);

            return user;
        }).toList();
        userSQLRepository.insertUserBatch(userList);
    }

    @Transactional
    public void deleteUserBatch() {
        userSQLRepository.deleteUser(101);
    }

    public List<User> findByName(String name) {
        UserQuery query = new UserQuery().name(name)
                .orderByAge(true);
        return userSQLRepository.findByQuery(query);
    }

}


```

全部代码完成。详细的，可运行的代码请参考示例项目`spring-boot-examples/spring-boot-example-querydsl-sql`


