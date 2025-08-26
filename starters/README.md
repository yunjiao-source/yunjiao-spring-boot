# Starters

基于Spring Boot的启动器

## starter-apijson-fastjson2

集成`APIJSON`框架的启动器，使用`apijson-fastjson2`插件

在`pom.xml`中添加依赖
```xml
<dependency>
    <groupId>io.gitee.yunjiao-source.spring-boot</groupId>
    <artifactId>starter-apijson-fastjson2</artifactId>
    <version>${version}</version>
</dependency>
```
所有的配置属性参考[application-all.yaml](../examples/example-apijson-fastjson2/src/main/resources/application-all.yml)

支持接口

| 接口url                 | 方法   | 说明                                               |
|-----------------------|------|--------------------------------------------------|
| common/{method}       | POST | 支持GET，HEAD，GETS，HEADS，POST，PUT，DELETE，CRUD等      |
| common/{method}/{tag} | POST | 增删改查统一接口，这个一个接口可替代 7 个万能通用接口，牺牲一些路由解析性能来提升一点开发效率 |
| ext/reload            | POST | 重新加载配置                                           |
| ext/post/verify       | POST | 生成验证码                                            |
| ext/gets/verify       | POST | 获取验证码                                            |
| ext/heads/verify      | POST | 校验验证码                                            |
| ext/login             | POST | 用户登录                                             |
| ext/logout            | POST | 退出登录，清空session                                   |
| ext/register          | POST | 注册                                               |
| ext/put/password      | POST | 设置密码                                             |

详细使用参考示例[example-apijson-fastjson2](../examples/example-apijson-fastjson2)

## starter-apijson-gson

集成`APIJSON`框架的启动器, 使用`apijson-gson`插件

在`pom.xml`中添加依赖
```xml
<dependency>
    <groupId>io.gitee.yunjiao-source.spring-boot</groupId>
    <artifactId>starter-apijson-gson</artifactId>
    <version>${version}</version>
</dependency>
```

所有的配置属性参考[application-all.yaml](../examples/example-apijson-gson/src/main/resources/application-all.yml)

支持的接口

| 接口url                 | 方法   | 说明                                               |
|-----------------------|------|--------------------------------------------------|
| common/{method}       | POST | 支持GET，HEAD，GETS，HEADS，POST，PUT，DELETE，CRUD等      |
| common/{method}/{tag} | POST | 增删改查统一接口，这个一个接口可替代 7 个万能通用接口，牺牲一些路由解析性能来提升一点开发效率 |
| ext/reload            | POST | 重新加载配置                                           |
| ext/post/verify       | POST | 生成验证码                                            |
| ext/gets/verify       | POST | 获取验证码                                            |
| ext/heads/verify      | POST | 校验验证码                                            |
| ext/login             | POST | 用户登录                                             |
| ext/logout            | POST | 退出登录，清空session                                   |
| ext/register          | POST | 注册                                               |
| ext/put/password      | POST | 设置密码                                             |

详细使用参考示例[example-apijson-gson](../examples/example-apijson-gson)

## start-id

在`pom.xml`中添加依赖
```xml
<dependency>
    <groupId>io.gitee.yunjiao-source.spring-boot</groupId>
    <artifactId>starter-hutool</artifactId>
    <version>${version}</version>
</dependency>
```

已配置的Bean列表

* Snowflake： 雪花算法。默认workerId=1，datacenterId=1。如需支持分布式，请设置系统环境变量：SNOWFLAKE_WORKER_ID 与 {SNOWFLAKE_DATACENTER_ID}。使用属性`spring.hutool.snowflak=false`可关闭配置

详细使用参考示例[example-hutool](../examples/example-hutool)

## start-querydsl-jpa

集成`QueryDSL JPA`框架的启动器。

在`pom.xml`中添加依赖
```xml
<dependency>
    <groupId>io.gitee.yunjiao-source.spring-boot</groupId>
    <artifactId>starter-querydsl-jpa</artifactId>
    <version>${version}</version>
</dependency>
```

已配置的Bean列表
* JPAQueryFactory： 查询工厂

详细使用参考示例[example-querydsl-jpa](../examples/example-querydsl-jpa)

## start-querydsl-sql

集成`QueryDSL SQL`框架的启动器。

在`pom.xml`中添加依赖
```xml
<dependency>
    <groupId>io.gitee.yunjiao-source.spring-boot</groupId>
    <artifactId>starter-querydsl-sql</artifactId>
    <version>${version}</version>
</dependency>
```

已配置的Bean列表
* SQLQueryFactory： 查询工厂

详细使用参考示例[example-querydsl-sql](../examples/example-querydsl-sql)

## start-captcha

验证码启动器， 在`pom.xml`中添加依赖
```xml
<dependency>
    <groupId>io.gitee.yunjiao-source.spring-boot</groupId>
    <artifactId>starter-captcha</artifactId>
    <version>${version}</version>
</dependency>
```

已配置的Bean列表
* CaptchaServiceFactory： 查询工厂
* LineCaptchaService：线段干扰的验证码服务
* CircleCaptchaService：圆圈干扰验证码服务
* ShearCaptchaService：扭曲干扰验证码服务
* GifCaptchaService：gif验证码服务

详细使用参考示例[example-captcha](../examples/example-captcha)