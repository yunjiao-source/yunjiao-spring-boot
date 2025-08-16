package io.yunjiao.spring.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.yunjiao.spring.querydsl.sql.QUsers;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link QSpecification} 测试用例
 *
 * @author yangyunjiao
 */
public class QSpecificationTest {

    @Test
    void givenOne_thenAnd() {
        QSpecification spec = name();
        assertThat(create(spec).toString()).isEqualTo("users.name = abc");
    }

    @Test
    void givenTrue_thenAnd() {
        QSpecification spec = name().and(age());
        assertThat(create(spec).toString()).isEqualTo("users.name = abc && users.age >= 18");
    }

    @Test
    void givenThree_thenAnd() {
        QSpecification spec = name().and(age()).and(email());
        assertThat(create(spec).toString()).isEqualTo("users.name = abc && users.age >= 18 && users.email = abc@qq.com");

        spec = name().and(age().and(email()));
        assertThat(create(spec).toString()).isEqualTo("users.name = abc && users.age >= 18 && users.email = abc@qq.com");
    }

    @Test
    void givenNull_thenAnd() {
        QSpecification spec = name().and(nullable()).and(age()).and(email());
        assertThat(create(spec).toString()).isEqualTo("users.name = abc && users.age >= 18 && users.email = abc@qq.com");

        spec = nullable().and(name().and(age()).and(email()));
        assertThat(create(spec).toString()).isEqualTo("users.name = abc && users.age >= 18 && users.email = abc@qq.com");
    }

    @Test
    void and() {
        QSpecification spec = nullable().and(age(), email(), birthDateEq());
        assertThat(create(spec).toString()).isEqualTo("users.age >= 18 && users.email = abc@qq.com && users.birthDate = 1970-01-01");

        spec = nullable().and(age(), email().and(birthDateEq()));
        assertThat(create(spec).toString()).isEqualTo("users.age >= 18 && users.email = abc@qq.com && users.birthDate = 1970-01-01");
    }

    @Test
    void andNot() {
        QSpecification spec = name().and(age()).andNot(email());
        assertThat(create(spec).toString()).isEqualTo("users.name = abc && users.age >= 18 && !(users.email = abc@qq.com)");

        spec = name().andNot(email().and(age()));
        assertThat(create(spec).toString()).isEqualTo("users.name = abc && !(users.email = abc@qq.com && users.age >= 18)");

        spec = name().and(age()).andNot(email().and(birthDateEq()));
        assertThat(create(spec).toString()).isEqualTo("users.name = abc && users.age >= 18 && !(users.email = abc@qq.com && users.birthDate = 1970-01-01)");

        spec = name().andNot(age(), email(), birthDateEq());
        assertThat(create(spec).toString()).isEqualTo("users.name = abc && !(users.age >= 18) && !(users.email = abc@qq.com) && !(users.birthDate = 1970-01-01)");

        spec = name().andNot(age(), email().or(birthDateEq()));
        assertThat(create(spec).toString()).isEqualTo("users.name = abc && !(users.age >= 18) && !(users.email = abc@qq.com || users.birthDate = 1970-01-01)");
    }

    @Test
    void andAnyOf() {
        QSpecification spec = name().andAnyOf(age(), email());
        assertThat(create(spec).toString()).isEqualTo("users.name = abc && (users.age >= 18 || users.email = abc@qq.com)");
    }

    @Test
    void orAllOf() {
        QSpecification spec = name().orAllOf(age(), email());
        assertThat(create(spec).toString()).isEqualTo("users.name = abc || users.age >= 18 && users.email = abc@qq.com");
    }

    @Test
    void not() {
        QSpecification spec = QSpecification.not(name());
        assertThat(create(spec).toString()).isEqualTo("!(users.name = abc)");

        spec = QSpecification.not(name().and(age()));
        assertThat(create(spec).toString()).isEqualTo("!(users.name = abc && users.age >= 18)");

        spec = QSpecification.not(name()).and(age());
        assertThat(create(spec).toString()).isEqualTo("!(users.name = abc) && users.age >= 18");

        spec = QSpecification.not(name()).and(age()).and(email());
        assertThat(create(spec).toString()).isEqualTo("!(users.name = abc) && users.age >= 18 && users.email = abc@qq.com");

        spec = QSpecification.not(name().and(age())).and(email());
        assertThat(create(spec).toString()).isEqualTo("!(users.name = abc && users.age >= 18) && users.email = abc@qq.com");

        spec = QSpecification.not(name().and(age())).or(email());
        assertThat(create(spec).toString()).isEqualTo("!(users.name = abc && users.age >= 18) || users.email = abc@qq.com");
    }

    @Test
    void or() {
        QSpecification spec = name();
        assertThat(create(spec).toString()).isEqualTo("users.name = abc");

        spec = name().or(age()).or(email());
        assertThat(create(spec).toString()).isEqualTo("users.name = abc || users.age >= 18 || users.email = abc@qq.com");

        spec = name().or(age().or(email()));
        assertThat(create(spec).toString()).isEqualTo("users.name = abc || users.age >= 18 || users.email = abc@qq.com");

        spec = name().or(age(), email());
        assertThat(create(spec).toString()).isEqualTo("users.name = abc || users.age >= 18 || users.email = abc@qq.com");
    }

    @Test
    void orNot() {
        QSpecification spec = name().orNot(age()).orNot(email()).and(birthDateEq());
        assertThat(create(spec).toString()).isEqualTo("(users.name = abc || !(users.age >= 18) || !(users.email = abc@qq.com)) && users.birthDate = 1970-01-01");

        spec = name().orNot(age()).orNot(email().and(birthDateEq()));
        assertThat(create(spec).toString()).isEqualTo("users.name = abc || !(users.age >= 18) || !(users.email = abc@qq.com && users.birthDate = 1970-01-01)");

        spec = name().orNot(age(), email(), birthDateEq());
        assertThat(create(spec).toString()).isEqualTo("users.name = abc || !(users.age >= 18) || !(users.email = abc@qq.com) || !(users.birthDate = 1970-01-01)");
    }

    @Test
    void orAnd() {
        QSpecification spec = name().or(age()).and(email());
        assertThat(create(spec).toString()).isEqualTo("(users.name = abc || users.age >= 18) && users.email = abc@qq.com");

        spec = name().or(age().and(email()));
        assertThat(create(spec).toString()).isEqualTo("users.name = abc || users.age >= 18 && users.email = abc@qq.com");

        spec = name().and(age().or(email()));
        assertThat(create(spec).toString()).isEqualTo("users.name = abc && (users.age >= 18 || users.email = abc@qq.com)");

        spec = name().and(age().or(email()));
        assertThat(create(spec).toString()).isEqualTo("users.name = abc && (users.age >= 18 || users.email = abc@qq.com)");

        spec = name().or(age(), email()).and(birthDateEq(), createdAt());
        assertThat(create(spec).toString()).isEqualTo("(users.name = abc || users.age >= 18 || users.email = abc@qq.com) && users.birthDate = 1970-01-01 && users.createdAt = 1970-01-01 08:00:00.0");
    }

    @Test
    void where() {
        QSpecification spec = QSpecification.where(name());
        assertThat(create(spec).toString()).isEqualTo("users.name = abc");

        spec = QSpecification.where(name().and(age()));
        assertThat(create(spec).toString()).isEqualTo("users.name = abc && users.age >= 18");
    }


    private Predicate create(QSpecification spec) {
        return spec.toPredicate(new BooleanBuilder());
    }

    private QSpecification name() {
        return (builder) -> {
            System.out.println("name");
            return QUsers.user.name.eq("abc");
        };
    }

    private QSpecification age() {
        return (builder) -> {
            System.out.println("age");
            return QUsers.user.age.goe(18);
        };
    }

    private QSpecification email() {
        return (builder) -> {
            System.out.println("email");
            return QUsers.user.email.eq("abc@qq.com");
        };
    }

    private QSpecification nullable() {
        return (builder) -> {
            System.out.println("null");
            return null;
        };
    }

    private QSpecification birthDateEq() {
        return (builder) -> {
            System.out.println("birthDate");
            return QUsers.user.birthDate.eq(new Date(0));
        };
    }

    private QSpecification createdAt() {
        return (builder) -> {
            System.out.println("createdAt");
            return QUsers.user.createdAt.eq(new Timestamp(0));
        };
    }
}
