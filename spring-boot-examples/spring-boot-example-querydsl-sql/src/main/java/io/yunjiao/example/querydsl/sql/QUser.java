package io.yunjiao.example.querydsl.sql;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

import javax.annotation.processing.Generated;
import java.sql.Types;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;




/**
 * QUser is a Querydsl query type for QUser
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUser extends com.querydsl.sql.RelationalPathBase<QUser> {

    private static final long serialVersionUID = 2115731614;

    public static final QUser user = new QUser("user");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final DatePath<java.sql.Date> birthDate = createDate("birthDate", java.sql.Date.class);

    public final DateTimePath<java.sql.Timestamp> createdAt = createDateTime("createdAt", java.sql.Timestamp.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final com.querydsl.sql.PrimaryKey<QUser> primary = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<QOrder> order = createInvForeignKey(id, "user_id");

    public QUser(String variable) {
        super(QUser.class, forVariable(variable), "null", "user");
        addMetadata();
    }

    public QUser(String variable, String schema, String table) {
        super(QUser.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUser(String variable, String schema) {
        super(QUser.class, forVariable(variable), schema, "user");
        addMetadata();
    }

    public QUser(Path<? extends QUser> path) {
        super(path.getType(), path.getMetadata(), "null", "user");
        addMetadata();
    }

    public QUser(PathMetadata metadata) {
        super(QUser.class, metadata, "null", "user");
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

