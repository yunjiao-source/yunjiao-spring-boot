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
