## start-querydsl-jpa

集成`QueryDSL JPA`框架的启动器。

* `JPAQueryRepositorySupport`仓库类，用户继承此类，集成功能：单个查询，列表查询，查询分页，查询Tuple，统计记录数，更新，删除等。支持数据库事务
* `QSpecification`接口，用于复杂条件查询，支持：and, or, not等。类似`Spring`框架的`Specification`


详细使用参考示例[example-querydsl-jpa](../../examples/example-querydsl-jpa)

## 使用Maven

在`pom.xml`中添加依赖
```xml
<dependency>
    <groupId>io.gitee.yunjiao-source.spring-boot</groupId>
    <artifactId>starter-querydsl-jpa</artifactId>
    <version>${version}</version>
</dependency>
```

