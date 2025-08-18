package io.yunjiao.example.querydsl.sql;

import io.yunjiao.extension.querydsl.QSpecification;

import java.sql.Date;

/**
 * 用户条件信息
 *
 * @author yangyunjiao
 */
public final class UserSpec {
    private static final QUsers qUser = QUsers.users;

    public static QSpecification idEq(Long id) {
        return builder -> qUser.id.eq(id);
    }

    public static QSpecification nameLike(String name) {
        return builder -> qUser.name.like(name);
    }

    public static QSpecification nameEq(String name) {
        return builder -> qUser.name.eq(name);
    }

    public static QSpecification ageGoe(Integer age) {
        return builder -> qUser.age.goe(age);
    }
    public static QSpecification birthDateEq(Date birthDate) {
        return builder -> qUser.birthDate.goe(birthDate);
    }

}
