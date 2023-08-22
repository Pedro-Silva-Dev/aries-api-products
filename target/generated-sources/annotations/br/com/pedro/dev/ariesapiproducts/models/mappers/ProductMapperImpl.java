package br.com.pedro.dev.ariesapiproducts.models.mappers;

import br.com.pedro.dev.ariesapiproducts.models.Product;
import br.com.pedro.dev.ariesapiproducts.models.requests.ProductRequest;
import br.com.pedro.dev.ariesapiproducts.models.responses.ProductResponse;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-21T21:20:27-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Private Build)"
)
public class ProductMapperImpl extends ProductMapper {

    @Override
    public Product toProduct(ProductRequest productCreateRequest) {
        if ( productCreateRequest == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.setName( productCreateRequest.name() );
        product.setDescription( productCreateRequest.description() );
        product.setPrice( productCreateRequest.price() );
        product.setStock( productCreateRequest.stock() );
        product.setActive( productCreateRequest.active() );
        product.setCategoryId( productCreateRequest.categoryId() );

        return product.build();
    }

    @Override
    public ProductResponse toProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.setId( product.getId() );
        productResponse.setName( product.getName() );
        productResponse.setDescription( product.getDescription() );
        productResponse.setPrice( product.getPrice() );
        productResponse.setStock( product.getStock() );
        productResponse.setActive( product.getActive() );
        if ( product.getCategoryId() != null ) {
            productResponse.setCategoryId( product.getCategoryId().intValue() );
        }

        return productResponse.build();
    }
}
