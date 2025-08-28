package yunjiao.springboot.project.rql.persistence.dao;

import lombok.Getter;
import yunjiao.springboot.project.rql.persistence.model.User;
import yunjiao.springboot.project.rql.web.util.SpecSearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

@Getter
public record UserSpecification(SpecSearchCriteria criteria) implements Specification<User> {

    public static Specification<User> empty() {
        return (root, query, builder) -> null;
    }

    @Override
    public Predicate toPredicate(final Root<User> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        return switch (criteria.getOperation()) {
            case EQUALITY -> builder.equal(root.get(criteria.getKey()), criteria.getValue());
            case NEGATION -> builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN -> builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN -> builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LIKE -> builder.like(root.get(criteria.getKey()), criteria.getValue().toString());
            case STARTS_WITH -> builder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
            case ENDS_WITH -> builder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
            case CONTAINS -> builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            default -> null;
        };
    }

}
