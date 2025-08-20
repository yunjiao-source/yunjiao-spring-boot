# spring-boot-starter-apijson-fastjson2


## 使用指南

参考示例项目`spring-boot-examples/spring-boot-example-apijson-fastjson2`

在pom.xml中添加

```xml

<dependency>
    <groupId>io.gitee.yunjiao-source</groupId>
    <artifactId>spring-boot-starter-apijson-fastjson2</artifactId>
    <version>${version}</version>
</dependency>
```

## 所有的配置属性

参考[application-all.yaml](../doc/apijson/application-all.yml)

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

请求示例

```curl
curl --location --request POST 'http://localhost:8080/api-json/common/get' \
--header 'Content-Type: application/json' \
--data-raw '{
  "Moment": {
    "id": 12
  }
}'
```

* 接口默认是开启的，可以关闭这些接口，配置属性 `spring.apijson.rest-api.enable=false`
* 接口添加了统一的前缀，默认是`api-json`，配置属性`spring.apijson.rest-api.prefix=YOURE-PREFIX`
* 在`doc/apijson`目录下有`apifox`及`postman`工具文件，导入json文件


