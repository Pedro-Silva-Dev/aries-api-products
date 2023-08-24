package br.com.pedro.dev.ariesapiproducts.models.mappers;

import br.com.pedro.dev.ariesapiproducts.models.Category;
import br.com.pedro.dev.ariesapiproducts.models.requests.CategoryRequest;
import br.com.pedro.dev.ariesapiproducts.models.responses.CategoryResponse;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-24T19:03:23-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.35.0.v20230721-1147, environment: Java 17.0.7 (Eclipse Adoptium)"
)
public class CategoryMapperImpl extends CategoryMapper {

    @Override
    public Category toCategory(CategoryRequest categoryCreateRequest) {
        if ( categoryCreateRequest == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.setActive( categoryCreateRequest.active() );
        category.setName( categoryCreateRequest.name() );

        return category.build();
    }

    @Override
    public CategoryResponse toCategoryResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse.CategoryResponseBuilder categoryResponse = CategoryResponse.builder();

        categoryResponse.setActive( category.getActive() );
        categoryResponse.setId( category.getId() );
        categoryResponse.setName( category.getName() );

        return categoryResponse.build();
    }
}
