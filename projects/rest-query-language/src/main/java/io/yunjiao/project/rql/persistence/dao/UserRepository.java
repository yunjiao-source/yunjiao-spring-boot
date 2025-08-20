package io.yunjiao.project.rql.persistence.dao;

import io.yunjiao.project.rql.persistence.model.User;
import io.yunjiao.project.rql.web.util.SearchCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    default List<User> searchUser(final List<SearchCriteria> params) {
        Specification<User> spec = (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            UserSearchQueryCriteriaConsumer searchConsumer = new UserSearchQueryCriteriaConsumer(predicate, builder, root);
            params.forEach(searchConsumer);
            return searchConsumer.getPredicate();
        };

        return this.findAll(spec);
    }

    boolean existsByFirstName(String john);
}
