package br.com.pedro.dev.ariesapiproducts.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPromotion is a Querydsl query type for Promotion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromotion extends EntityPathBase<Promotion> {

    private static final long serialVersionUID = 1989332335L;

    public static final QPromotion promotion = new QPromotion("promotion");

    public final BooleanPath active = createBoolean("active");

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> dhc = createDateTime("dhc", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> dhf = createDateTime("dhf", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> dhi = createDateTime("dhi", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> dhu = createDateTime("dhu", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigDecimal> discount = createNumber("discount", java.math.BigDecimal.class);

    public final BooleanPath discountPercentage = createBoolean("discountPercentage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QPromotion(String variable) {
        super(Promotion.class, forVariable(variable));
    }

    public QPromotion(Path<? extends Promotion> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPromotion(PathMetadata metadata) {
        super(Promotion.class, metadata);
    }

}

