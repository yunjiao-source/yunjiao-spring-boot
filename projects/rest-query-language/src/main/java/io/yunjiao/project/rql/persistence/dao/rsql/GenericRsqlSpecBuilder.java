package io.yunjiao.project.rql.persistence.dao.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.LogicalOperator;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;

public class GenericRsqlSpecBuilder<T> {

    public Specification<T> createSpecification(final Node node) {
        if (node instanceof LogicalNode) {
            return createSpecification((LogicalNode) node);
        }
        if (node instanceof ComparisonNode) {
            return createSpecification((ComparisonNode) node);
        }
        return null;
    }

    public Specification<T> createSpecification(final LogicalNode logicalNode) {

        List<Specification<T>> specs = logicalNode.getChildren()
                .stream()
                .map(this::createSpecification)
                .filter(Objects::nonNull)
                .toList();

        Specification<T> result = specs.get(0);
        if (logicalNode.getOperator() == LogicalOperator.AND) {
           for (int i = 1; i < specs.size(); i++) {
               result = result.and(specs.get(i));
           }
        }
        else if (logicalNode.getOperator() == LogicalOperator.OR) {
           for (int i = 1; i < specs.size(); i++) {
               result = result.or(specs.get(i));
           }
        }

        return result;
    }

    public Specification<T> createSpecification(final ComparisonNode comparisonNode) {
        return new GenericRsqlSpecification<T>(comparisonNode.getSelector(), comparisonNode.getOperator(), comparisonNode.getArguments());
    }

}
