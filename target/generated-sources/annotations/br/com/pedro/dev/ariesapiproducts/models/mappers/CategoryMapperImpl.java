package br.com.pedro.dev.ariesapiproducts.models.mappers;

import br.com.pedro.dev.ariesapiproducts.models.Category;
import br.com.pedro.dev.ariesapiproducts.models.requests.CategoryRequest;
import br.com.pedro.dev.ariesapiproducts.models.responses.CategoryResponse;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-21T21:20:27-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Private Build)"
)
public class CategoryMapperImpl extends CategoryMapper {

    @Override
    public Category toCategory(CategoryRequest categoryCreateRequest) {
        if ( categoryCreateRequest == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.setName( categoryCreateRequest.name() );
        category.setActive( categoryCreateRequest.active() );

        return category.build();
    }

    @Override
    public CategoryResponse toCategoryResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse.CategoryResponseBuilder categoryResponse = CategoryResponse.builder();

        categoryResponse.setId( category.getId() );
        categoryResponse.setName( category.getName() );
        categoryResponse.setActive( category.getActive() );

        return categoryResponse.build();
    }
}
