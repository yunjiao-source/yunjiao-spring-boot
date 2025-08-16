package io.yunjiao.example.querydsl.sql;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

import javax.annotation.processing.Generated;
import java.sql.Types;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;




/**
 * QOrder is a Querydsl query type for QOrder
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QOrder extends com.querydsl.sql.RelationalPathBase<QOrder> {

    private static final long serialVersionUID = 1157598427;

    public static final QOrder order = new QOrder("order");

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath status = createString("status");

    public final DateTimePath<java.sql.Timestamp> transactionTime = createDateTime("transactionTime", java.sql.Timestamp.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final com.querydsl.sql.PrimaryKey<QOrder> primary = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<QUser> user = createForeignKey(userId, "id");

    public QOrder(String variable) {
        super(QOrder.class, forVariable(variable), "null", "order");
        addMetadata();
    }

    public QOrder(String variable, String schema, String table) {
        super(QOrder.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QOrder(String variable, String schema) {
        super(QOrder.class, forVariable(variable), schema, "order");
        addMetadata();
    }

    public QOrder(Path<? extends QOrder> path) {
        super(path.getType(), path.getMetadata(), "null", "order");
        addMetadata();
    }

    public QOrder(PathMetadata metadata) {
        super(QOrder.class, metadata, "null", "order");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(amount, ColumnMetadata.named("amount").withIndex(5).ofType(Types.DECIMAL).withSize(10).withDigits(2));
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(status, ColumnMetadata.named("status").withIndex(3).ofType(Types.CHAR).withSize(9).notNull());
        addMetadata(transactionTime, ColumnMetadata.named("transaction_time").withIndex(4).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(userId, ColumnMetadata.named("user_id").withIndex(2).ofType(Types.BIGINT).withSize(19).notNull());
    }

}

