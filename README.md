# Spring Boot

基于`Spring Boot`框架，集成其他框架开发的`Starter`

## 版本说明

第一个的版本是`3.5.x.1`,前面两位是`Spring Boot`的版本，随着`Spring Boot`的版本升级而变。最后一位是本框架的版本，随着框架的扩展而增加，如：1,2,3，...x
不会因`Spring Boot`版本变动而变动。

如果需要 `Spring Boot` 3.5之前的包，请`fork`本项目，克隆代码，自己编译需要的版本。

## 项目列表

| 项目                                    | 说明                                | 起始版本 |
|---------------------------------------|-----------------------------------|------|
| spring-boot-autoconfigure             | 自动配置                              | 1    |
| spring-boot-dependencies              | 项目依赖                              | 1    |
| spring-boot-examples                  | 示例项目                              | 1    |
| spring-querydsl                       | QueryDSL 集成                       | 1    |
| spring-apijson                        | APIJSON 集成                        | 2    |
| config/checkstyle                     | 代码检查配置                            | 1    |
| config/examples-db                    | 示例所需的数据库脚本                        | 1    |
| spring-boot-starter-querydsl-jpa      | QueryDSL JPA Spring Boot 启动器      | 1    |
| spring-boot-starter-querydsl-sql      | QueryDSL SQL Spring Boot 启动器      | 1    |
| spring-boot-starter-hutool            | Hutool Spring Boot 启动器            | 2    |
| spring-boot-starter-apijson-fastjson2 | APIJSON Fastjson2 Spring Boot 启动器 | 2    |
| spring-boot-starter-apijson-gson      | APIJSON Gson Spring Boot 启动器      | 2    |

## 如何编译

在IDE中，使用maven插件查看项目结构，有三个项目

```text
Spring Boot
Spring Boot :: Examples
Spring Boot :: Starter Parent
```

这三个项目需要分别编译，首先在主目录执行

```shell
cd yunjiao-spring-boot
mvn clean
mvn flatten:flatten
mvn install

cd spring-boot-starter-parent
mvn clean
mvn flatten:flatten
mvn install

cd ..
cd spring-boot-examples
mvn clean
mvn flatten:flatten
mvn install
```

务必按照此顺序编译打包！！！

## 使用 maven

有两个方式引入本项目依赖

```text
    <!-- 第一种方式(推荐)：使用本父项目-->
    <parent>
        <groupId>io.gitee.yunjiao-source</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>${你需要的版本}</version>
    </parent>
    
    <!-- 第二种方式：导入项目依赖清单-->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>io.gitee.yunjiao-source</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${你需要的版本}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>    
```

## 使用指南

* spring-boot-starter-querydsl-jpa [使用指南](./spring-boot-starter-querydsl-jpa/README.md) [参考项目](./examples/example-querydsl-jpa)
* spring-boot-starter-querydsl-sql [使用指南](./spring-boot-starter-querydsl-sql/README.md) [参考项目](./examples/example-querydsl-sql)
* spring-boot-starter-hutool [使用指南](./spring-boot-starter-hutool/README.md) [参考项目](./examples/example-hutool)
* spring-boot-starter-apijson-fastjson2 [使用指南](./spring-boot-starter-apijson-fastjson2/README.md) [参考项目](./examples/example-apijson-fastjson2)
* spring-boot-starter-apijson-Gson [使用指南](./spring-boot-starter-apijson-gson/README.md) [参考项目](./examples/example-apijson-gons)

## 参考

* [开发过程中遇到的问题](./FAQ.md)


