# spring-boot-starter-hutool

基于Hutool框架的自动配置Starter

## 版本历史

| 起始版本 | 依赖包及版本             | 说明 |
|------|--------------------|----|
| 2    | hutool-core-5.8.38 ||

## maven

在`pom.xml`中添加依赖

```xml
        <dependency>
            <groupId>io.gitee.yunjiao-source</groupId>
            <artifactId>spring-boot-starter-hutool</artifactId>
        </dependency>
```

## 使用指南

### Snowflake

Twitter的Snowflake 算法。

自动注入
```java
    @Autowired
    private Snowflake snowflake;

    @Override
    public void run(String... args) throws Exception {
        log.info("Snowflake = {}", snowflake.nextId());
    }
```

支持`Long`类型及字符类型

关闭配置

```yaml
spring:
  hutool:
    snowflake: true # 默认true
```



