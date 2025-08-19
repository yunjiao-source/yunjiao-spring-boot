package io.yunjiao.project.rql.basic.querydsl;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import io.yunjiao.project.rql.basic.QUserEntity;
import io.yunjiao.project.rql.basic.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

/**
 * TODO
 *
 * @author yangyunjiao
 */
public interface UserQuerydslRepository extends JpaRepository<UserEntity, Long>,
        QuerydslPredicateExecutor<UserEntity>, QuerydslBinderCustomizer<QUserEntity> {
    @Override
    default void customize(
            QuerydslBindings bindings, QUserEntity root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.excluding(root.email);
    }
}
