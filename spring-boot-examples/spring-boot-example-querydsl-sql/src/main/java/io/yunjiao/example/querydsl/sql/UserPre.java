package io.yunjiao.example.querydsl.sql;

import com.querydsl.core.types.Predicate;
import io.yunjiao.spring.querydsl.QSpecification;

import java.sql.Date;

/**
 * 用户条件信息，如果不想使用{@link QSpecification}接口
 *
 * @author yangyunjiao
 */
public final class UserPre {
    private static final QUser qUser = QUser.user;

    public static Predicate nameLike(String name) {
        return qUser.name.like(name);
    }

    public static Predicate nameEq(String name) {
        return qUser.name.eq(name);
    }

    public static Predicate ageGoe(Integer age) {
        return qUser.age.goe(age);
    }
    public static Predicate birthDate(Date birthDate) {
        return qUser.birthDate.goe(birthDate);
    }

    public static QSpecification idEq(Long id) {
        return builder -> qUser.id.eq(id);
    }
}
