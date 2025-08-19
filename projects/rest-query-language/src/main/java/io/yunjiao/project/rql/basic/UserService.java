package io.yunjiao.project.rql.basic;

import io.yunjiao.project.rql.basic.jpa.SearchCriteria;
import io.yunjiao.project.rql.basic.jpa.UserJpaRepository;
import io.yunjiao.project.rql.basic.jpa.UserSearchQueryCriteriaConsumer;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务
 *
 * @author yangyunjiao
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;

    public List<UserEntity> searchUser(List<SearchCriteria> params) {
        Specification<UserEntity> spec = (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            UserSearchQueryCriteriaConsumer searchConsumer =
                    new UserSearchQueryCriteriaConsumer(predicate, builder, root);
            params.forEach(searchConsumer);
            return searchConsumer.getPredicate();
        };

        return userJpaRepository.findAll(spec);
    }
}
