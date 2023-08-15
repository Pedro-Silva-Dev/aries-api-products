package br.com.pedro.dev.ariesapiproducts.controllers;

import java.util.List;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedro.dev.ariesapiproducts.config.requests.BadRequestException;
import br.com.pedro.dev.ariesapiproducts.config.requests.NotAcceptableRequestException;
import br.com.pedro.dev.ariesapiproducts.config.requests.NotFoundRequestException;
import br.com.pedro.dev.ariesapiproducts.models.Category;
import br.com.pedro.dev.ariesapiproducts.models.mappers.CategoryMapper;
import br.com.pedro.dev.ariesapiproducts.models.requests.CategoryRequest;
import br.com.pedro.dev.ariesapiproducts.models.responses.CategoryResponse;
import br.com.pedro.dev.ariesapiproducts.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    
    private final CategoryService categoryService;


    /**
     * 
     * @param name
     * @param active
     * @param pageable
     * @return
     */
    @GetMapping("/page.json")
    public ResponseEntity<Page<CategoryResponse>> getCategoryPage(@RequestParam(required = false) String name, @RequestParam(required = false) Boolean active, Pageable pageable) {
        Page<Category> categoryPage = categoryService.getPageCategory(name, active, pageable);
        List<CategoryResponse> categoryResponseList = categoryPage.getContent().stream().map(CategoryMapper.INSTANCE::toCategoryResponse).toList();
        PageImpl<CategoryResponse> page = new PageImpl<CategoryResponse>(categoryResponseList, categoryPage.getPageable(), categoryPage.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK.value()).body(page);
    }

    /**
     * 
     * @param name
     * @param active
     * @return
     */
    @GetMapping("/list.json")
    public ResponseEntity<List<CategoryResponse>> getCategoryList(@RequestParam(required = false) String name, @RequestParam(required = false) Boolean active) {
        List<Category> categoryPage = categoryService.getListCategory(name, active);
        List<CategoryResponse> list = categoryPage.stream().map(CategoryMapper.INSTANCE::toCategoryResponse).toList();
        return ResponseEntity.status(HttpStatus.OK.value()).body(list);
    }

    /**
     * 
     * @param id
     * @return
     */
    @GetMapping("/{id}.json")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if(category != null) {
            CategoryResponse categoryResponse = CategoryMapper.INSTANCE.toCategoryResponse(category);
            return ResponseEntity.status(HttpStatus.OK.value()).body(categoryResponse);
        }
        throw new NotFoundRequestException("Category not found.");
    }

    /**
     * 
     * @param categoryRequest
     * @return
     */
    @PostMapping("/create.json")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        Category category = categoryService.createCategory(categoryRequest);
        if(category != null) {
            CategoryResponse categoryResponse = CategoryMapper.INSTANCE.toCategoryResponse(category);
            return ResponseEntity.status(HttpStatus.CREATED.value()).body(categoryResponse);
        }
        throw new NotAcceptableRequestException("Category cannot have an ID to register.");
    }

    /**
     * 
     * @param id
     * @param categoryRequest
     * @return
     */
    @PutMapping("/{id}.json")
    public ResponseEntity<CategoryResponse> updateAddress(@PathVariable Long id, @RequestBody @Valid CategoryRequest categoryRequest) {
        Category category = categoryService.updateCategory(id, categoryRequest);
        if(category != null) {
            CategoryResponse categoryResponse = CategoryMapper.INSTANCE.toCategoryResponse(category);
            return ResponseEntity.status(HttpStatus.OK.value()).body(categoryResponse);
        }
        throw new NotFoundRequestException("Category not found for update.");
    }

}
