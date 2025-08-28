## start-id

ID生成启动器，集成`Hutool`等框架

* Snowflake： 雪花算法。默认workerId=1，datacenterId=1。如需支持分布式，请设置系统环境变量：SNOWFLAKE_WORKER_ID 与 {SNOWFLAKE_DATACENTER_ID}。使用属性`spring.hutool.snowflak=false`可关闭配置

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





