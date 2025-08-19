package io.yunjiao.project.rql.basic.querydsl;

import com.querydsl.core.types.dsl.*;
import io.yunjiao.project.rql.basic.UserEntity;
import io.yunjiao.project.rql.basic.jpa.SearchCriteria;
import lombok.AllArgsConstructor;


/**
 * TODO
 *
 * @author yangyunjiao
 */
@AllArgsConstructor
public class UserPredicate {
    private SearchCriteria criteria;

    public BooleanExpression getPredicate() {
        PathBuilder<UserEntity> entityPath = (new PathBuilderFactory()).create(UserEntity.class);

        if (isNumeric(criteria.getValue().toString())) {
            NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
            int value = Integer.parseInt(criteria.getValue().toString());
            switch (criteria.getOperation()) {
                case ":":
                    return path.eq(value);
                case ">":
                    return path.goe(value);
                case "<":
                    return path.loe(value);
            }
        }
        else {
            StringPath path = entityPath.getString(criteria.getKey());
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                return path.containsIgnoreCase(criteria.getValue().toString());
            }
        }
        return null;
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        // 匹配整数、小数（含正负号）
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
