package br.com.pedro.dev.ariesapiproducts.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.pedro.dev.ariesapiproducts.models.Product;
import br.com.pedro.dev.ariesapiproducts.models.requests.ProductRequest;
import br.com.pedro.dev.ariesapiproducts.models.responses.ProductResponse;

@Mapper(componentModel = "productMapper")
public abstract class ProductMapper {
    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    public abstract Product toProduct(ProductRequest productCreateRequest);
    public abstract ProductResponse toProductResponse(Product product);
}
