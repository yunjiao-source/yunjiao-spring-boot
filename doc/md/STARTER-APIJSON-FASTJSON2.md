# starter-apijson-fastjson2

集成`APIJSON`框架的启动器, 使用`apijson-fastjson2`插件

* 方便配置。在`application.yml`中配置参数，避免写死在程序中。所有的配置属性参考[application-all.yaml](../../examples/example-apijson-fastjson2/src/main/resources/application-all.yml)
* 支持数据库连接池
* 提供多个接口，如：CRUD，登录， 登出等

详细使用参考示例[example-apijson-fastjson2](../../examples/example-apijson-fastjson2)

## 使用Maven
在`pom.xml`中添加依赖
```xml
<dependency>
    <groupId>io.gitee.yunjiao-source.spring-boot</groupId>
    <artifactId>starter-apijson-fastjson2</artifactId>
    <version>${version}</version>
</dependency>
```

## 支持的接口


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


