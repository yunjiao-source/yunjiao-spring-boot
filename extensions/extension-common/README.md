# spring-common

通用工具集。

## maven

```xml
<dependency>
    <groupId>io.gitee.yunjiao-source</groupId>
    <artifactId>spring-common</artifactId>
    <version>${revision}</version>
</dependency>
```
## 使用指南

### TimestampIdGenerator

当前时间戳的ID生成器，`Long类型`，提供静态方法生成ID。线程安全的，仅用于测试或示例

```text
        final int SIZE = 3000;
        Set<Long> idSet = IntStream.range(0, SIZE).parallel().mapToLong(i -> TimestampIdGenerator.next()).boxed()
                .collect(Collectors.toSet());
        System.out.println(idSet.stream().skip(SIZE -10).collect(Collectors.toList()));
```

输出：
```text
[1755402971097584, 1755402971097585, 1755402971097598, 1755402971097599, 1755402971097596, 1755402971097597, 1755402971097594, 1755402971097595, 1755402971097592, 1755402971097593]
```
