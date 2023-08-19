package br.com.pedro.dev.ariesapiproducts.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 408510651L;

    public static final QProduct product = new QProduct("product");

    public final BooleanPath active = createBoolean("active");

    public final NumberPath<Integer> categoryId = createNumber("categoryId", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> dhc = createDateTime("dhc", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> dhu = createDateTime("dhu", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<java.math.BigDecimal> price = createNumber("price", java.math.BigDecimal.class);

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

