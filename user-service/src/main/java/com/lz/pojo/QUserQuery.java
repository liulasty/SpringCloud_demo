package com.lz.pojo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUSER用户
 *
 * @author lz
 * @date 2024/07/10 15:46:30
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserQuery extends EntityPathBase<User> {

    private static final long serialVersionUID = -509001982L;

    public static final QUserQuery user = new QUserQuery("user");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath sex = createString("sex");

    public QUserQuery(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUserQuery(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserQuery(PathMetadata metadata) {
        super(User.class, metadata);
    }

}