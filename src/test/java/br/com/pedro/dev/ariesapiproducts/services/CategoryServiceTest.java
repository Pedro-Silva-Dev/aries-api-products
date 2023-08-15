package br.com.pedro.dev.ariesapiproducts.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.querydsl.core.BooleanBuilder;

import br.com.pedro.dev.ariesapiproducts.models.Category;
import br.com.pedro.dev.ariesapiproducts.models.requests.CategoryRequest;
import br.com.pedro.dev.ariesapiproducts.repositories.CategoryRepository;
import br.com.pedro.dev.ariesapiproducts.utils.CategoryCreateTest;

@DisplayName("Testar o service de Categoria.")
@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepositoryMock;

    @BeforeEach
    void setup() {
        PageImpl<Category> pageable = new PageImpl<>(List.of(CategoryCreateTest.getCategoryValid(1L)));

        BDDMockito.when(categoryRepositoryMock.findAll(ArgumentMatchers.any(BooleanBuilder.class), ArgumentMatchers.any(Pageable.class))).thenReturn(pageable);
        BDDMockito.when(categoryRepositoryMock.findAll(ArgumentMatchers.any(BooleanBuilder.class))).thenReturn(List.of(CategoryCreateTest.getCategoryValid(1L)));
        BDDMockito.when(categoryRepositoryMock.findById(1L)).thenReturn(Optional.of(CategoryCreateTest.getCategoryValid(1L)));
        BDDMockito.when(categoryRepositoryMock.save(ArgumentMatchers.any())).thenReturn(CategoryCreateTest.getCategoryValid(1L));
    }

    @Test
    @DisplayName("Obter uma pagina de categoria")
    void getCategoryPageTest() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Category> result = categoryService.getPageCategory(null, null, pageable);

        assertThat(result).isNotEmpty();
        assertThat(result.getContent().get(0)).isNotNull();
        assertThat(result.getContent().get(0).getName()).isNotNull();
        assertThat(result.getContent().get(0).getActive()).isTrue();
    }

    @Test
    @DisplayName("Obter uma pagina de categoria por status")
    void getCategoryByStatusPageTest() {
        Boolean status = CategoryCreateTest.getCategoryValid(1L).getActive();
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Category> result = categoryService.getPageCategory(null, status, pageable);

        assertThat(result).isNotEmpty();
        assertThat(result.getContent().get(0)).isNotNull();
        assertThat(result.getContent().get(0).getName()).isNotNull();
        assertThat(result.getContent().get(0).getActive()).isEqualTo(status);
    }

    @Test
    @DisplayName("Obter uma lista de categoria")
    void getCategoryListTest() {
        List<Category> result = categoryService.getListCategory(null, null);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getName()).isNotNull();
        assertThat(result.get(0).getActive()).isTrue();
    }


    @Test
    @DisplayName("Obter uma lista de categoria por nome")
    void getCategoryByNameListTest() {
        String name = CategoryCreateTest.getCategoryValid(1L).getName();
        List<Category> result = categoryService.getListCategory(name, null);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getName()).isNotNull();
        assertThat(result.get(0).getName()).isEqualTo(name);
        assertThat(result.get(0).getActive()).isTrue();
    }

    @Test
    @DisplayName("Obter uma categoria por id")
    void getCategoryByIdTest() {
        Category result = categoryService.getCategoryById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isNotNull();
        assertThat(result.getActive()).isTrue();
    }

    @Test
    @DisplayName("Cadastrar uma categoria")
    void createCategoryTest() {
        CategoryRequest categoryRequest = CategoryRequest.builder()
        .setName(CategoryCreateTest.getCategoryValid(1L).getName())
        .setActive(true)
        .build();

        Category result = categoryService.createCategory(categoryRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isPositive();
        assertThat(result.getName()).isEqualTo(categoryRequest.name());
        assertThat(result.getActive()).isTrue();
    }

    @Test
    @DisplayName("Atualizar uma categoria")
    void updateCategoryTest() {
        CategoryRequest categoryRequest = CategoryRequest.builder()
        .setName(CategoryCreateTest.getCategoryValid(1L).getName())
        .setActive(true)
        .build();

        Category result = categoryService.updateCategory(1L, categoryRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isPositive();
        assertThat(result.getName()).isEqualTo(categoryRequest.name());
        assertThat(result.getActive()).isTrue();
    }

}
