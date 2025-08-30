# FAQ

* Table "USERS" not found
```text
Caused by: org.h2.jdbc.JdbcSQLSyntaxErrorException: Table "USERS" not found (candidates are: "user"); SQL statement:
select user.age, user.birthDate, user.created_at, user.email, user.id, user.name
from user user
where user.id = ?
limit ? [42103-232]
```
使用`H2`作为单元测试时，表名称大小写的问题，`from "user"` 这种写法是小写的表， `from user`是大写的表。
在`url`上添加`;DATABASE_TO_UPPER=FALSE`可以解决问题

* EntityManager 没有初始化
```text
   JPAQueryFactoryAutoConfiguration:
      Did not match:
         - @ConditionalOnSingleCandidate (types: jakarta.persistence.EntityManager; SearchStrategy: all) did not find any beans (OnBeanCondition)
      Matched:
         - @ConditionalOnClass found required classes 'com.querydsl.jpa.impl.JPAQueryFactory', 'jakarta.persistence.EntityManager' (OnClassCondition)
```
尽量不要在配置类上使用 `@ConditionalOnSingleCandidate` 或 `@ConditionalOnMissingBean`, spring会自动判断Bean初始化的顺序

* [ERROR] Some problems were encountered while processing the POMs
```text
[ERROR] [ERROR] Some problems were encountered while processing the POMs:
[FATAL] Non-resolvable parent POM for io.gitee.yunjiao-source:spring-boot-examples:${revision}: The following artifacts could not be resolved: io.gitee.yunjiao-source:spring-boot-starter-parent:pom:${revision} (absent): io.gitee.yunjiao-source:spring-boot-starter-parent:pom:${revision} was not found in https://repo.maven.apache.org/maven2 during a previous attempt. This failure was cached in the local repository and resolution is not reattempted until the update interval of central has elapsed or updates are forced and 'parent.relativePath' points at no local POM @ line 5, column 13
 @ 
```
这是因为spring-boot-examples项目并不是一个子项目，他是一个独立的项目。解决问题的方法是使用flatten-maven-plugin插件

```shell
mvn clean
```
clean命令可能失败，不用管他

```shell
mvn flatten:flatten
```
flatten命令必须成功

```shell
mvn install
```

* examples项目install时出现错误
```text
examples项目install时出现错误

[INFO] Spring Boot :: Examples ............................ SUCCESS [  0.441 s]
[INFO] Example :: Hutool .................................. SUCCESS [  1.773 s]
[INFO] Example :: QueryDsl SQL ............................ FAILURE [  0.481 s]
[INFO] Example :: QueryDsl JPA ............................ SKIPPED
[INFO] Example :: APIJSON Fastjson2 ....................... SKIPPED
[INFO] Example :: APIJSON Gson ............................ SKIPPED
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.041 s
[INFO] Finished at: 2025-08-18T11:24:54+08:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal com.querydsl:querydsl-maven-plugin:5.1.0:export (default) on project example-querydsl-sql: Communications link failure
[ERROR] 
[ERROR] The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server.: Connection refused: connect
[ERROR] -> [Help 1]
```
这是因为`QueryDsl SQL`编译时会生成Q类，这需要数据库连接，在`pom.xml`中修改数据库连接信息

* Attempt to recreate a file for type yunjiao.springboot.example.querydsl.jpa.QOrder
```text
Attempt to recreate a file for type yunjiao.springboot.example.querydsl.jpa.QOrder
```

通常在编译，安装项目时出现此异常，解决方法是`pom.xml` 文件中移除 `apt-maven-plugin` 即可

* 运行`extension-querydsl`测试时异常
```text
java.lang.IllegalArgumentException: org.hibernate.query.sqm.UnknownEntityException: Could not resolve root entity 'User'

	at org.hibernate.internal.ExceptionConverterImpl.convert(ExceptionConverterImpl.java:143)
	at org.hibernate.internal.ExceptionConverterImpl.convert(ExceptionConverterImpl.java:167)
	at org.hibernate.internal.ExceptionConverterImpl.convert(ExceptionConverterImpl.java:173)
	at org.hibernate.internal.AbstractSharedSessionContract.createQuery(AbstractSharedSessionContract.java:886)
	at org.hibernate.internal.AbstractSharedSessionContract.createQuery(AbstractSharedSessionContract.java:796)
	at org.hibernate.internal.AbstractSharedSessionContract.createQuery(AbstractSharedSessionContract.java:143)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at org.springframework.orm.jpa.ExtendedEntityManagerCreator$ExtendedEntityManagerInvocationHandler.invoke(ExtendedEntityManagerCreator.java:364)
	at jdk.proxy2/jdk.proxy2.$Proxy57.createQuery(Unknown Source)
	at com.querydsl.jpa.impl.AbstractJPAQuery.createQuery(AbstractJPAQuery.java:132)
	at com.querydsl.jpa.impl.AbstractJPAQuery.createQuery(AbstractJPAQuery.java:125)
	at com.querydsl.jpa.impl.AbstractJPAQuery.fetch(AbstractJPAQuery.java:242)
	at yunjiao.springboot.extension.querydsl.jpa.JPAQueryCurdExecutor.findList(JPAQueryCurdExecutor.java:393)
	at yunjiao.springboot.extension.querydsl.jpa.JPAQueryCurdExecutor.findList(JPAQueryCurdExecutor.java:346)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:360)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:196)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:380)
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:728)
	at yunjiao.springboot.extension.querydsl.jpa.JPAQueryCurdExecutor$$SpringCGLIB$$0.findList(<generated>)
	at yunjiao.springboot.extension.querydsl.jpa.JPAQueryRepositorySupportTest$DemoJPAQueryRepositorySupport.findUserByOrderStatus(JPAQueryRepositorySupportTest.java:193)
	at yunjiao.springboot.extension.querydsl.jpa.JPAQueryRepositorySupportTest.whenFindUserByOrderStatus(JPAQueryRepositorySupportTest.java:124)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
Caused by: org.hibernate.query.sqm.UnknownEntityException: Could not resolve root entity 'User'
	at org.hibernate.query.hql.internal.SemanticQueryBuilder.resolveRootEntity(SemanticQueryBuilder.java:2129)
	at org.hibernate.query.hql.internal.SemanticQueryBuilder.visitRootEntity(SemanticQueryBuilder.java:2059)
	at org.hibernate.query.hql.internal.SemanticQueryBuilder.visitRootEntity(SemanticQueryBuilder.java:277)
	at org.hibernate.grammars.hql.HqlParser$RootEntityContext.accept(HqlParser.java:2814)
	at org.hibernate.query.hql.internal.SemanticQueryBuilder.visitEntityWithJoins(SemanticQueryBuilder.java:2029)
	at org.hibernate.query.hql.internal.SemanticQueryBuilder.visitFromClause(SemanticQueryBuilder.java:2016)
	at org.hibernate.query.hql.internal.SemanticQueryBuilder.visitQuery(SemanticQueryBuilder.java:1248)
	at org.hibernate.query.hql.internal.SemanticQueryBuilder.visitQuerySpecExpression(SemanticQueryBuilder.java:1040)
	at org.hibernate.query.hql.internal.SemanticQueryBuilder.visitQuerySpecExpression(SemanticQueryBuilder.java:277)
	at org.hibernate.grammars.hql.HqlParser$QuerySpecExpressionContext.accept(HqlParser.java:2134)
	at org.hibernate.query.hql.internal.SemanticQueryBuilder.visitSimpleQueryGroup(SemanticQueryBuilder.java:1025)
	at org.hibernate.query.hql.internal.SemanticQueryBuilder.visitSimpleQueryGroup(SemanticQueryBuilder.java:277)
	at org.hibernate.grammars.hql.HqlParser$SimpleQueryGroupContext.accept(HqlParser.java:2005)
	at org.hibernate.query.hql.internal.SemanticQueryBuilder.visitSelectStatement(SemanticQueryBuilder.java:492)
	at org.hibernate.query.hql.internal.SemanticQueryBuilder.visitStatement(SemanticQueryBuilder.java:451)
	at org.hibernate.query.hql.internal.SemanticQueryBuilder.buildSemanticModel(SemanticQueryBuilder.java:324)
	at org.hibernate.query.hql.internal.StandardHqlTranslator.translate(StandardHqlTranslator.java:71)
	at org.hibernate.query.internal.QueryInterpretationCacheStandardImpl.createHqlInterpretation(QueryInterpretationCacheStandardImpl.java:145)
	at org.hibernate.query.internal.QueryInterpretationCacheStandardImpl.resolveHqlInterpretation(QueryInterpretationCacheStandardImpl.java:132)
	at org.hibernate.query.spi.QueryEngine.interpretHql(QueryEngine.java:54)
	at org.hibernate.internal.AbstractSharedSessionContract.interpretHql(AbstractSharedSessionContract.java:832)
	at org.hibernate.internal.AbstractSharedSessionContract.createQuery(AbstractSharedSessionContract.java:878)
	... 26 more
```

测试用例JPAQueryRepositorySupportTest中，hibernate扫描路径需要修改

```text
        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
......
            em.setPackagesToScan("io.yunjiao");
            改成
            em.setPackagesToScan("yunjiao.springboot");
......
            return em;
        }
```

* 发布时异常
```text
mvn clean install -P release

[INFO] --- gpg:1.6:sign (default) @ spring-boot ---
gpg: can't connect to the keyboxd: IPC connect call failed
gpg: error opening key DB: No Keybox daemon running
gpg: no default secret key: Input/output error
gpg: signing failed: Input/output error
```

安装的GPG版本（可能是较新的2.x版本）与Maven插件试图调用的方式不兼容。 将插件`maven-gpg-plugin`的版本`1.6`升级到`3.2.8`

* 配置了多数据源，初始化数据源失败

```text
Caused by: java.lang.IllegalArgumentException: jdbcUrl is required with driverClassName.
	at com.zaxxer.hikari.HikariConfig.validate(HikariConfig.java:1022)
	at com.zaxxer.hikari.HikariDataSource.getConnection(HikariDataSource.java:109)
	at org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource.getConnection(AbstractRoutingDataSource.java:194)
```

HikariCP 要求使用 jdbc-url 而不是常见的 url 来指定数据库连接字符串。在单数据源情况下，Spring Boot 的自动配置会做转换，但在多数据源手动配置中，你需要显式使用正确的属性键。

单数据源配置

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/apijson?userSSL=false&serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8
    username: root
    password: root
```

多数据源配置
```yaml
spring:
  datasource:
    master:
      driver-class-name: com.mysql.cj.jdbc.Driver
      jdbc-url: jdbc:mysql://localhost:3306/apijson?userSSL=false&serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8
      username: root
      password: root

```