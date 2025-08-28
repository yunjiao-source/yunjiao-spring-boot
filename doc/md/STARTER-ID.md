## start-id

ID生成启动器，集成`Hutool`, `Uid-Generator`等框架


* Snowflake： `Hutool`实现的雪花算法。
* UidGeneratorCached，UidGeneratorDefault：分别包装了`Uid-Generator`框架的`CachedUidGenerator`与`DefaultUidGenerator`，实现雪花算法。包装是因为这样可以方便的注入

详细使用参考示例[example-id](../../examples/example-id)

## 使用Maven

在`pom.xml`中添加依赖
```xml
<dependency>
    <groupId>io.gitee.yunjiao-source.spring-boot</groupId>
    <artifactId>starter-id</artifactId>
    <version>${version}</version>
</dependency>
```





