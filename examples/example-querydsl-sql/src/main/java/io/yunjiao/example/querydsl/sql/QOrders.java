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
 * QOrders is a Querydsl query type for QOrders
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QOrders extends com.querydsl.sql.RelationalPathBase<QOrders> {

    private static final long serialVersionUID = 1525812984;

    public static final QOrders orders = new QOrders("orders");

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath status = createString("status");

    public final DateTimePath<java.sql.Timestamp> transactionTime = createDateTime("transactionTime", java.sql.Timestamp.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final com.querydsl.sql.PrimaryKey<QOrders> primary = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<QUsers> user = createForeignKey(userId, "id");

    public QOrders(String variable) {
        super(QOrders.class, forVariable(variable), "null", "orders");
        addMetadata();
    }

    public QOrders(String variable, String schema, String table) {
        super(QOrders.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QOrders(String variable, String schema) {
        super(QOrders.class, forVariable(variable), schema, "orders");
        addMetadata();
    }

    public QOrders(Path<? extends QOrders> path) {
        super(path.getType(), path.getMetadata(), "null", "orders");
        addMetadata();
    }

    public QOrders(PathMetadata metadata) {
        super(QOrders.class, metadata, "null", "orders");
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

