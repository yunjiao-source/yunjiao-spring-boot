package io.yunjiao.spring.querydsl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Querdsl 工具
 *
 * @author yangyunjiao
 */
public final class QueryDSLUtils {
    /**
     * 创建{@link OrderSpecifier}实例
     * @param asc 升序=true，降序=false
     * @param path 必须值
     * @return 实例
     */
    public static OrderSpecifier<?> orderBy(boolean asc, Path<?> path) {
        return new OrderSpecifier(asc ? Order.ASC : Order.DESC, path);
    }

    /**
     * 转换
     *
     * @param sort 必须值
     * @param pathBuilder 必须值
     * @return {@link OrderSpecifier}数组
     */
    public static OrderSpecifier<?>[] toOrderSpecifiers(Sort sort, PathBuilder<?> pathBuilder) {
        if (Objects.isNull(sort)) {
            return new OrderSpecifier[0];
        }
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        for (Sort.Order sortOrder : sort) {
            // 处理嵌套属性（如 "address.city"）
            String[] properties = sortOrder.getProperty().split("\\.");
            PathBuilder<?> currentPath = pathBuilder;
            for (int i = 0; i < properties.length - 1; i++) {
                currentPath = currentPath.get(properties[i]);
            }

            // 获取最终属性路径
            String finalProperty = properties[properties.length - 1];
            Expression<?> propertyPath = currentPath.get(finalProperty);

            // 处理排序方向
            Order direction = sortOrder.isAscending() ? Order.ASC : Order.DESC;
            orderSpecifiers.add(new OrderSpecifier(direction, propertyPath));
        }

        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }
}
