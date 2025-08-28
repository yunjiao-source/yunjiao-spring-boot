# start-captcha

验证码生成启动器，集成`Hutool`，`aj-captcha`等框架

* 零配置。无需在yaml中配置参数，框架提供默认值
* 支持多种验证码，包括：线段干扰验证码，圆圈干扰验证码，扭曲干扰验证码，GIF验证码，滑块拼图验证码，文字点选验证码，旋转拼图验证码
* 提供丰富的配置参数，支持用户自定义。所有的配置参数参考[application-all.yml](../../examples/example-captcha/src/main/resources/application-all.yml)

详细使用参考示例[example-captcha](../../examples/example-captcha)

## 使用Maven

在`pom.xml`中添加依赖
```xml
<dependency>
    <groupId>io.gitee.yunjiao-source.spring-boot</groupId>
    <artifactId>starter-captcha</artifactId>
    <version>${version}</version>
</dependency>
```



