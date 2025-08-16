# Spring Boot

基于`Spring Boot`框架，集成其他框架开发的`Starter`

## 版本说明

第一个的版本是`3.5.x.1`,前面两位是`Spring Boot`的版本，随着`Spring Boot`的版本升级而变得。最后一位是本框架的版本，随着框架的扩展而增加，如：1,2,3，...x
不会因`Spring Boot`版本变动而变动。

如果需要 `Spring Boot` 3.5之前的包，请`fork`本项目，克隆代码，自己编译需要的版本。

## 项目列表

| 项目                               | 说明                           | 起始版本 |
|----------------------------------|------------------------------|------|
| spring-boot-autoconfigure        | 自动配置                         | 1    |
| spring-boot-dependencies         | 项目依赖                         | 1    |
| spring-boot-bom                  | 框架清单                         | 1    |
| spring-boot-examples             | 示例项目                         | 1    |
| spring-querydsl                  | QueryDSL 集成                  | 1    |
| config/checkstyle                | 代码检查配置                       | 1    |
| config/examples-db               | 示例所需的数据库脚本                   | 1    |
| spring-boot-starter-querydsl-jpa | QueryDSL JPA Spring Boot 启动器 | 1    |
| spring-boot-starter-querydsl-sql | QueryDSL SQL Spring Boot 启动器 | 1    |

## maven

有两个方式引入本项目依赖，在你的父项目pom.xml文件中：

```text
    <!-- 第一种方式(推荐)：使用本父项目-->
    <parent>
        <groupId>io.gitee.yunjiao-source</groupId>
        <artifactId>spring-boot-examples</artifactId>
        <version>${你需要的版本}</version>
    </parent>
    
    <!-- 第二种方式：导入项目依赖清单-->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>io.gitee.yunjiao-source</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${yunjiao-spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>    
```

## 使用指南

* [spring-boot-starter-querydsl-jpa](./spring-boot-starter-querydsl-jpa/README.md)
* [spring-boot-starter-querydsl-sql](./spring-boot-starter-querydsl-sql/README.md)

## 参考

* [开发过程中遇到的问题](./FAQ.md)


