# projects

很多有意思的应用，都来自网络。

## rest-query-language

“REST Query Language”通常指的是一种用于对RESTful API的资源进行查询、过滤、排序等操作的语言或语法规范。它的核心目的是让客户端能够更灵活、
高效地获取他们需要的特定数据，而不是简单地获取服务器返回的完整资源表示。

参考文章[Building a REST Query Language](https://www.baeldung.com/spring-rest-api-query-search-language-tutorial)

使用指南

http://localhost:8080/users/criteria?search=lastName:doe,age%3E25
http://localhost:8080/users/spec?search=lastName:doe,age%3E25
http://localhost:8080/users/espec?search=lastName:doe,age%3E25
http://localhost:8080/users/querydsl?search=lastName:doe,age%3E25
http://localhost:8080/users/rsql?search=lastName==do*;age%3E25

http://localhost:8080/users/api/querydsl?firstName=john
http://localhost:8080/users/api/querydsl?age=22&age=26

http://localhost:8080/users/spec/adv?search=( firstName:john OR firstName:tom ) AND age%3E22





