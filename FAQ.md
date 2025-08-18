# FAQ

```text
Caused by: org.h2.jdbc.JdbcSQLSyntaxErrorException: Table "USERS" not found (candidates are: "user"); SQL statement:
select user.age, user.birthDate, user.created_at, user.email, user.id, user.name
from user user
where user.id = ?
limit ? [42103-232]
```
使用`H2`作为单元测试时，表名称大小写的问题，`from "user"` 这种写法是小写的表， `from user`是大写的表。
在`url`上添加`;DATABASE_TO_UPPER=FALSE`可以解决问题

```text
   JPAQueryFactoryAutoConfiguration:
      Did not match:
         - @ConditionalOnSingleCandidate (types: jakarta.persistence.EntityManager; SearchStrategy: all) did not find any beans (OnBeanCondition)
      Matched:
         - @ConditionalOnClass found required classes 'com.querydsl.jpa.impl.JPAQueryFactory', 'jakarta.persistence.EntityManager' (OnClassCondition)
```
尽量不要在配置类上使用 `@ConditionalOnSingleCandidate` 或 `@ConditionalOnMissingBean`, spring会自动判断Bean初始化的顺序

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

```text
Attempt to recreate a file for type io.yunjiao.example.querydsl.jpa.QOrder
```

通常在编译，安装项目时出现此异常，解决方法是`pom.xml` 文件中移除 `apt-maven-plugin` 即可

