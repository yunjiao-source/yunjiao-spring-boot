# Spring Boot

基于`Spring Boot`框架，集成其他框架开发的`Starter`

## 发布版本

| 项目版本    | String Boot 版本 |
|---------|----------------|
| <=0.4.0 | 3.0.13         |



如果需要 `Spring Boot` 3.5之前的包，请`fork`本项目，克隆代码，自己编译需要的版本。例如编译`Spring Boot 3.5.4`的版本
```shell
mvn clean install -P spring-boot-3.5.4
```

## 项目列表

| 项目                                    | 说明                                |
|---------------------------------------|-----------------------------------|
| autoconfigure                         | 自动配置                              |
| dependencies                          | 项目依赖                              |
| projects                              | 应用                                |
| examples                              | 示例项目                              |
| extensions                            | 扩展                                |
| config                                | 配置                                |
| starters                              | 启动器                               |


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
        <groupId>io.gitee.yunjiao-source.spring-boot</groupId>
        <artifactId>starter-parent</artifactId>
        <version>${你需要的版本}</version>
    </parent>
    
    <!-- 第二种方式：导入项目依赖清单-->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>io.gitee.yunjiao-source.spring-boot</groupId>
                <artifactId>dependencies</artifactId>
                <version>${你需要的版本}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>    
```

## 使用指南

* starter-apijson-fastjson2 [使用指南](./doc/md/STARTER-APIJSON-FASTJSON2.md)
* starter-apijson-gson [使用指南](./doc/md/STARTER-APIJSON-GSON.md)
* starter-captcha [使用指南](./doc/md/STARTER-CAPTCHA.md)
* starter-id [使用指南](./doc/md/STARTER-ID.md)
* starter-querydsl-jpa [使用指南](./doc/md/STARTER-QUERYDSL-JPA.md)
* starter-querydsl-sql [使用指南](./doc/md/STARTER-QUERYDSL-SQL.md)

## 参考

* [开发过程中遇到的问题](./FAQ.md)
* [参考文章](./ARTICLE.md)


