# spring-boot-example-querydsl-jpa

querydsl-jpa示例项目，所需的数据库脚本在主项目`config/examples-db`下可以找到

## 生成`Q`文件

修改`pom.xml`中的数据库连接，然后执行

```shell
mvn clean compile
```

在`target/generated-sources/java`目录下有生成的文件

## 参考
* [`querydsl-jpa`官方文档](http://querydsl.com/static/querydsl/latest/reference/html/ch02.html)

## FAQ

```text
Attempt to recreate a file for type io.yunjiao.example.querydsl.jpa.QOrder
```

`pom.xml` 文件中移除 `apt-maven-plugin` 即可
