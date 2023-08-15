package br.com.pedro.dev.ariesapiproducts.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;

import br.com.pedro.dev.ariesapiproducts.models.Category;
import br.com.pedro.dev.ariesapiproducts.models.QCategory;
import br.com.pedro.dev.ariesapiproducts.models.mappers.CategoryMapper;
import br.com.pedro.dev.ariesapiproducts.models.requests.CategoryRequest;
import br.com.pedro.dev.ariesapiproducts.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    /**
     * 
     * @param name
     * @param active
     * @param pageable
     * @return
     */
    public Page<Category> getPageCategory(String name, Boolean active, Pageable pageable) {
        QCategory qCategory = QCategory.category;
        BooleanBuilder builder = new BooleanBuilder();

        if(name != null) {
            builder.and(qCategory.name.toLowerCase().like(("%"+name+"%").toLowerCase()));
        }
        if(active != null) {
            builder.and(qCategory.active.eq(active));
        }
        return categoryRepository.findAll(builder, pageable);
    }

    /**
     * 
     * @param name
     * @param active
     * @return
     */ 
    public List<Category> getListCategory(String name, Boolean active) {
        QCategory qCategory = QCategory.category;
        BooleanBuilder builder = new BooleanBuilder();

        if(name != null) {
            builder.and(qCategory.name.toLowerCase().like(("%"+name+"%").toLowerCase()));
        }
        if(active != null) {
            builder.and(qCategory.active.eq(active));
        }
        return (List<Category>) categoryRepository.findAll(builder);
    }

    /**
     * 
     * @param id
     * @return
     */
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    /**
     * 
     * @param categoryRequest
     * @return
     */
    public Category createCategory(CategoryRequest categoryRequest) {
        Category category = CategoryMapper.INSTANCE.toCategory(categoryRequest);
        return categoryRepository.save(category);
    }

    /**
     * 
     * @param id
     * @param categoryRequest
     * @return
     */
    public Category updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = getCategoryById(id);
        if(category != null) {
            category.setName(categoryRequest.name());
            category.setActive(categoryRequest.active());
            return categoryRepository.save(category);
        }
        return null;
    }

}
