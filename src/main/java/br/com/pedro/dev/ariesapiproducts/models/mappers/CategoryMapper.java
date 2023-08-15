package br.com.pedro.dev.ariesapiproducts.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.pedro.dev.ariesapiproducts.models.Category;
import br.com.pedro.dev.ariesapiproducts.models.requests.CategoryRequest;
import br.com.pedro.dev.ariesapiproducts.models.responses.CategoryResponse;

@Mapper(componentModel = "categoryMapper")
public abstract class CategoryMapper {
    public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    public abstract Category toCategory(CategoryRequest categoryCreateRequest);
    public abstract CategoryResponse toCategoryResponse(Category category);
}
