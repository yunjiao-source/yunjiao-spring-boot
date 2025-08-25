# extensions

开源的框架扩展，使其有利于`Spring Boot`集成

## extension-apijson

[APIJSON](http://apijson.cn/) 实现实时零代码接口和文档JSON 协议 与 ORM 库

如何使用请参考启动器[starter-apijson-fastjson2](../starters/starter-apijson-fastjson2) 与 [starter-apijson-gson](../starters/starter-apijson-gson)

### IdKeyStrategy接口

主键名称策略接口，接口只有一个方法
```java
String getIdKey(String database, String schema, String datasource, String table);
```
用于获取主键名称。一般情况下，数据库表的主键名称是`id`，但很多不是这样的，如：`User`表的主键名是`user_id`, `Order`表
的主键名称是`order_id`，这些都是很正常的，通用的名称。

在表主键名称不一样的情况下，需要实现此接口，根据`table`参数判断，返回该表的主键名称。

如果表的主键都是一样的，且名称都是`id`，那么你可以直接使用`IdKeyApijsonStrategy`实现类

### NewIdStrategy接口

主键值策略，接口只有一个方法

```java
Serializable newId(RequestMethod method, String database, String schema, String datasource, String table);
```

接口有多个实现类
* NewIdUuidStrategy：uuid主键策略
* NewIdTimestampStrategy：时间戳主键策略
* NewIdSnowflakeStrategy：雪花算法主键策略
* NewIdDatabaseStrategy：数据库主键策略，使用数据库功能生成主键


## extension-id

ID扩展，集成`Hutool`框架中`SnowFlake`类。

如何使用请参考启动器[starter-id](../starters/starter-id)

## extension-querydsl

[QueryDSL](http://querydsl.com/) 是一个框架，可以构建静态类型的 SQL 类查询。无需将查询编写为内联字符串或将它们外部化为 XML 文件，
它们可以通过 `Querydsl` 之类的流畅 API 构建。

如何使用请参考启动器[starter-querydsl-jpa](../starters/starter-querydsl-jpa) 与 [starter-querydsl-sql](../starters/starter-querydsl-sql)

重要的扩展如下：
### QSpecification接口

仿`Spring`框架`Specification`接口，实现条件的组合
```text
QSpecification spec = name().and(age()).and(email());
"users.name = abc && users.age >= 18 && users.email = abc@qq.com"

QSpecification spec = name().and(age()).andNot(email());
"users.name = abc && users.age >= 18 && !(users.email = abc@qq.com)"

QSpecification spec = name().andAnyOf(age(), email());
"users.name = abc && (users.age >= 18 || users.email = abc@qq.com)"
```
详细功能查看单元测试用例[QSpecificationTest](./extension-querydsl/src/test/java/io/yunjiao/extension/querydsl/QSpecificationTest.java)

### SQLQueryRepositorySupport抽象类

基于SQL的仓库支持，实现了对数据CURD操作，并且每个操作都添加了事务(Transactional)支持

* findOnlyOne：查询仅一条记录，如果有两条以上，抛出异常
* findFirstOne：查询第一条记录
* findMustOne：查询必须的一条记录，如果不存在记录，抛出异常
* count：统计记录数
* exist：是否存在记录
* findList：列表查询，支持查询条件，排序
* findTuple：指定字段列表查询，支持查询条件，排序
* findPage：分页查询，支持查询条件，排序
* update：更新操作
* delete：删除操作

用户应该继承此类，便拥有以上的全部功能

```java
    @Repository
    static class DemoSQLQueryRepositorySupport extends SQLQueryRepositorySupport {
        private final static QUsers user = QUsers.user;
        private final static QOrders order = QOrders.order;
    
        // 主键查询
        public User findById(Long id) {
            SQLQuery<User> query = select(Projections.bean(User.class, user.all()))
                    .from(user)
                    .where(user.id.eq(id));
            return getCurdExecutor().findMustOne(query);
        }

        // 多条件,排序查询
        public List<User> findList(String name, Integer age, Date birthDate) {
            SQLQuery<User> query = select(Projections.bean(User.class, user.all()))
                    .from(user);
            QSpecification spec = nameLike(name).and(ageGoe(age), birthDate(birthDate));
            QSort sort = QSort.by(user.age.asc(), user.birthDate.desc());
            return getCurdExecutor().findList(query, spec, sort);
        }

        // 分页查询, 使用QPageRequest对象
        public Page<User> findQPage(Integer age) {
            SQLQuery<User> query = select(Projections.bean(User.class, user.all()))
                    .from(user);
            QSort sort = QSort.by(user.age.desc());
            return getCurdExecutor().findPage(query, QPageRequest.of(0, 5, sort), ageGoe(age));
        }
    
        // 分页查询, 使用PageRequest对象
        public Page<User> findPage(Integer age) {
            SQLQuery<User> query = select(Projections.bean(User.class, user.all()))
                    .from(user);
            QSort sort = QSort.by(user.age.asc());
            return findPage(query, PageRequest.of(0, 5, sort), ageGoe(age));
        }
        
        // 关联查询：查询已完成订单的用户信息
        public List<User> findUserByOrderStatus() {
            SQLQuery<User> query = select(Projections.bean(User.class, user.all()))
                    .distinct()
                    .from(user)
                    .innerJoin(user.order, order)
                    .where(order.status.eq("completed"));
            return getCurdExecutor().findList(query);
        }
    }
```

详细功能查看单元测试用例[SQLQueryRepositorySupportTest](./extension-querydsl/src/test/java/io/yunjiao/extension/querydsl/sql/SQLQueryRepositorySupportTest.java)

### JPAQueryRepositorySupport抽象类

基于JPA的仓库支持，实现了对数据CURD操作，并且每个操作都添加了事务(Transactional)支持
* findOnlyOne：查询仅一条记录，如果有两条以上，抛出异常
* findFirstOne：查询第一条记录
* findMustOne：查询必须的一条记录，如果不存在记录，抛出异常
* count：统计记录数
* exist：是否存在记录
* findList：列表查询，支持查询条件，排序
* findTuple：指定字段列表查询，支持查询条件，排序
* findPage：分页查询，支持查询条件，排序
* update：更新操作
* delete：删除操作

用户应该继承此类，便拥有以上的全部功能

```java
    @Repository
    static class DemoJPAQueryRepositorySupport extends JPAQueryRepositorySupport {
        // 主键查询
        public User findById(Long id) {
            JPAQuery<User> query = selectFrom(user)
                    .where(user.id.eq(id));
            return getCurdExecutor().findMustOne(query);
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
    }
```
详细功能查看单元测试用例[JPAQueryRepositorySupportTest](./extension-querydsl/src/test/java/io/yunjiao/extension/querydsl/jpa/JPAQueryRepositorySupportTest.java)

## extension-common

通用工具扩展

### TimestampIdGenerator

当前时间戳的ID生成器，`Long类型`，提供静态方法生成ID。线程安全的，仅用于测试或示例

```text
        final int SIZE = 3000;
        Set<Long> idSet = IntStream.range(0, SIZE).parallel().mapToLong(i -> TimestampIdGenerator.next()).boxed()
                .collect(Collectors.toSet());
        System.out.println(idSet.stream().skip(SIZE -10).collect(Collectors.toList()));
```

输出：
```text
[1755402971097584, 1755402971097585, 1755402971097598, 1755402971097599, 1755402971097596, 1755402971097597, 1755402971097594, 1755402971097595, 1755402971097592, 1755402971097593]
```

### GaussianBlur

高斯模糊算法，用于图片，可以设置模糊程度

### EnumCache

枚举缓存，实现高效的枚举转换，如：名称转枚举，代码转枚举等

在定义枚举时
```text
enum StatusEnum {
        ......

        static {
            // 通过名称构建缓存,通过EnumCache.findByName(StatusEnum.class,"SUCCESS",null);调用能获取枚举
            EnumCache.registerByName(StatusEnum.class, StatusEnum.values());
            // 通过code构建缓存,通过EnumCache.findByValue(StatusEnum.class,"S",null);调用能获取枚举
            EnumCache.registerByValue(StatusEnum.class, StatusEnum.values(), StatusEnum::getCode);
        }
    }
```

### CaptchaService

验证码服务接口，主要功能包括验证码生成，校验等

* CaptchaData draw() : 验证码生成
* boolean verify(Object orignalCode, Object userCode)：验证码校验
* CaptchaCategory getCategory()：分类



## extension-captcha

验证码扩展，集成Hutool-captcha框架

如何使用请参考启动器[starter-captcha](../starters/starter-captcha)

集成Hutool框架验证码
* CircleCaptchaService: 线段干扰的验证码
* GifCaptchaService: 圆圈干扰验证码
* LineCaptchaService: gif验证码
* ShearCaptchaService: 扭曲干扰验证码


