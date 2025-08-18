package io.yunjiao.example.querydsl.jpa;

import io.yunjiao.extension.querydsl.QSpecification;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.querydsl.QSort;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static io.yunjiao.example.querydsl.jpa.UserSpec.*;
import static io.yunjiao.extension.querydsl.QueryDSLUtils.orderBy;

/**
 * 用户查询条件
 *
 * @author yangyunjiao
 */
@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class UserQuery {
    private final QUser qUser = QUser.user;
    private String name;
    private Integer minAge;
    private Boolean orderByCreateAt;
    private Boolean orderByAge;


    public QSpecification buildQSpecification() {
        QSpecification spec = QSpecification.where();
        if (StringUtils.hasText(name)) {
            spec = spec.and(nameLike(name));
        }
        if (Objects.nonNull(minAge)) {
            spec = spec.and(ageGoe(minAge));
        }
        return spec;
    }

    public QSort buildQsort() {
        QSort sort = new QSort();
        if (Objects.nonNull(orderByAge)) {
            sort = sort.and(orderBy(orderByAge, qUser.age));
        }
        if (Objects.nonNull(orderByCreateAt)) {
            sort = sort.and(orderBy(orderByCreateAt, qUser.createdAt));
        }
        return sort;
    }
}
