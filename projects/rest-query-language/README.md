# rest-query-language

[Building a REST Query Language](https://www.baeldung.com/spring-rest-api-query-search-language-tutorial)

## 使用指南

http://localhost:8080/users/criteria?search=lastName:doe,age%3E25
http://localhost:8080/users/spec?search=lastName:doe,age%3E25
http://localhost:8080/users/espec?search=lastName:doe,age%3E25
http://localhost:8080/users/querydsl?search=lastName:doe,age%3E25
http://localhost:8080/users/rsql?search=lastName==do*;age%3E25

http://localhost:8080/users/api/querydsl?firstName=john
http://localhost:8080/users/api/querydsl?age=22&age=26

http://localhost:8080/users/spec/adv?search=( firstName:john OR firstName:tom ) AND age%3E22


