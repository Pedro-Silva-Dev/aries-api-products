package br.com.pedro.dev.ariesapiproducts.models.mappers;

import br.com.pedro.dev.ariesapiproducts.models.Product;
import br.com.pedro.dev.ariesapiproducts.models.requests.ProductRequest;
import br.com.pedro.dev.ariesapiproducts.models.responses.ProductResponse;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-19T22:14:21-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.35.0.v20230721-1147, environment: Java 17.0.7 (Eclipse Adoptium)"
)
public class ProductMapperImpl extends ProductMapper {

    @Override
    public Product toProduct(ProductRequest productCreateRequest) {
        if ( productCreateRequest == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.setActive( productCreateRequest.active() );
        product.setCategoryId( productCreateRequest.categoryId() );
        product.setDescription( productCreateRequest.description() );
        product.setName( productCreateRequest.name() );
        product.setPrice( productCreateRequest.price() );
        product.setStock( productCreateRequest.stock() );

        return product.build();
    }

    @Override
    public ProductResponse toProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.setActive( product.getActive() );
        if ( product.getCategoryId() != null ) {
            productResponse.setCategoryId( product.getCategoryId().intValue() );
        }
        productResponse.setDescription( product.getDescription() );
        productResponse.setId( product.getId() );
        productResponse.setName( product.getName() );
        productResponse.setPrice( product.getPrice() );
        productResponse.setStock( product.getStock() );

        return productResponse.build();
    }
}
