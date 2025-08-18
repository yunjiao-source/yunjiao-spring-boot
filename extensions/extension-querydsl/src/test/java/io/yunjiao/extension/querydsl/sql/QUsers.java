package io.yunjiao.extension.querydsl.sql;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;
import com.querydsl.sql.ForeignKey;
import com.querydsl.sql.PrimaryKey;
import com.querydsl.sql.RelationalPathBase;

import javax.annotation.processing.Generated;
import java.sql.Types;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QUser is a Querydsl query type for QUser
 */
@Generated("codegen.MetaDataSerializer")
public class QUsers extends RelationalPathBase<QUsers> {

    private static final long serialVersionUID = 2115731614;

    public static final QUsers user = new QUsers("users");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final DatePath<java.sql.Date> birthDate = createDate("birthDate", java.sql.Date.class);

    public final DateTimePath<java.sql.Timestamp> createdAt = createDateTime("createdAt", java.sql.Timestamp.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final PrimaryKey<QUsers> primary = createPrimaryKey(id);

    public final ForeignKey<QOrders> order = createInvForeignKey(id, "user_id");

    public QUsers(String variable) {
        super(QUsers.class, forVariable(variable), "null", "users");
        addMetadata();
    }

    public QUsers(String variable, String schema, String table) {
        super(QUsers.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUsers(String variable, String schema) {
        super(QUsers.class, forVariable(variable), schema, "users");
        addMetadata();
    }

    public QUsers(Path<? extends QUsers> path) {
        super(path.getType(), path.getMetadata(), "null", "users");
        addMetadata();
    }

    public QUsers(PathMetadata metadata) {
        super(QUsers.class, metadata, "null", "users");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(age, ColumnMetadata.named("age").withIndex(4).ofType(Types.INTEGER).withSize(10));
        addMetadata(birthDate, ColumnMetadata.named("birth_date").withIndex(5).ofType(Types.DATE).withSize(10));
        addMetadata(createdAt, ColumnMetadata.named("created_at").withIndex(6).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(email, ColumnMetadata.named("email").withIndex(3).ofType(Types.VARCHAR).withSize(100));
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(name, ColumnMetadata.named("name").withIndex(2).ofType(Types.VARCHAR).withSize(50).notNull());
    }

}

