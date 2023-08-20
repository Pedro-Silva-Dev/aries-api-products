package br.com.pedro.dev.ariesapiproducts.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.querydsl.core.BooleanBuilder;

import br.com.pedro.dev.ariesapiproducts.models.Category;
import br.com.pedro.dev.ariesapiproducts.models.QCategory;
import br.com.pedro.dev.ariesapiproducts.utils.CategoryCreateTest;

@Testcontainers
@DisplayName("Testar o repositorio de Categoria.")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository; 

    @LocalServerPort
    private int port;

    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.26"))
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .waitingFor(Wait.forListeningPort())
            .withEnv("MYSQL_ROOT_HOST", "%");
    
    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
    }

    @DisplayName("Remover todas as categorias depois de cada teste.")
    @AfterEach
    void removeAfterEach() {
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("Obter uma pagina de todas as categorias")
    void getPageCategoryTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        categoryRepository.save(category);

        PageRequest pageable = PageRequest.of(0, 10);
        Page<Category> result = categoryRepository.findAll(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getTotalElements()).isPositive();
        assertThat(result.getSize()).isEqualTo(pageable.getPageSize());
        assertThat(result.getContent().get(0)).isNotNull();
        assertThat(result.getContent().get(0).getId()).isPositive();
        assertThat(result.getContent().get(0).getName()).isNotNull();
        assertThat(result.getContent().get(0).getActive()).isNotNull();
        assertThat(result.getContent().get(0).getDhc()).isNotNull();
        assertThat(result.getContent().get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma pagina de todas as categorias filtrando por nome")
    void getPageCategoryFilterByNameTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        categoryRepository.save(category);

        PageRequest pageable = PageRequest.of(0, 10);
        QCategory qCategory = QCategory.category;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qCategory.name.eq(category.getName()));

        Page<Category> result = categoryRepository.findAll(builder, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getTotalElements()).isPositive();
        assertThat(result.getSize()).isEqualTo(pageable.getPageSize());
        assertThat(result.getContent().get(0)).isNotNull();
        assertThat(result.getContent().get(0).getId()).isPositive();
        assertThat(result.getContent().get(0).getName()).isNotNull();
        assertThat(result.getContent().get(0).getActive()).isNotNull();
        assertThat(result.getContent().get(0).getDhc()).isNotNull();
        assertThat(result.getContent().get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma pagina de todas as categorias filtrando por nome e status")
    void getPageCategoryFilterByNameAndStatusTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        categoryRepository.save(category);

        PageRequest pageable = PageRequest.of(0, 10);
        QCategory qCategory = QCategory.category;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qCategory.name.eq(category.getName()));
        builder.and(qCategory.active.eq(category.getActive()));

        Page<Category> result = categoryRepository.findAll(builder, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getTotalElements()).isPositive();
        assertThat(result.getSize()).isEqualTo(pageable.getPageSize());
        assertThat(result.getContent().get(0)).isNotNull();
        assertThat(result.getContent().get(0).getId()).isPositive();
        assertThat(result.getContent().get(0).getName()).isNotNull();
        assertThat(result.getContent().get(0).getActive()).isNotNull();
        assertThat(result.getContent().get(0).getDhc()).isNotNull();
        assertThat(result.getContent().get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma lista de todas as categorias")
    void getListCategoryTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        categoryRepository.save(category);

        List<Category> result = categoryRepository.findAll();

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getId()).isPositive();
        assertThat(result.get(0).getName()).isNotNull();
        assertThat(result.get(0).getActive()).isNotNull();
        assertThat(result.get(0).getDhc()).isNotNull();
        assertThat(result.get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma lista de todas as categorias filtrando por nome")
    void getListCategoryFilterByNameTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        categoryRepository.save(category);

        QCategory qCategory = QCategory.category;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qCategory.name.eq(category.getName()));

        List<Category> result = (List<Category>) categoryRepository.findAll(builder);

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getId()).isPositive();
        assertThat(result.get(0).getName()).isNotNull();
        assertThat(result.get(0).getActive()).isNotNull();
        assertThat(result.get(0).getDhc()).isNotNull();
        assertThat(result.get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma lista de todas as categorias filtrando por nome e status")
    void getListCategoryFilterByNameAndStatusTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        categoryRepository.save(category);

        QCategory qCategory = QCategory.category;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qCategory.name.eq(category.getName()));
        builder.and(qCategory.active.eq(category.getActive()));

        List<Category> result = (List<Category>) categoryRepository.findAll(builder);

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getId()).isPositive();
        assertThat(result.get(0).getName()).isNotNull();
        assertThat(result.get(0).getActive()).isNotNull();
        assertThat(result.get(0).getDhc()).isNotNull();
        assertThat(result.get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma categoria por id")
    void getCategoryByIdTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);

        Optional<Category> result = categoryRepository.findById(categorySaved.getId());

        assertThat(result).isNotNull();
        assertThat(result).isPresent();
        assertThat(result.get()).isNotNull();
        assertThat(result.get().getId()).isPositive();
        assertThat(result.get().getName()).isNotNull();
        assertThat(result.get().getActive()).isNotNull();
        assertThat(result.get().getDhc()).isNotNull();
        assertThat(result.get().getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma categoria por id invalido")
    void getCategoryByIdInvalidTest() {
        Optional<Category> result = categoryRepository.findById(0L);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Atualizar uma categoria por id")
    void updateCategoryByIdTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);

        categorySaved.setActive(false);
        Category result = categoryRepository.save(categorySaved);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isPositive();
        assertThat(result.getName()).isNotNull();
        assertThat(result.getActive()).isFalse();
        assertThat(result.getDhc()).isEqualTo(categorySaved.getDhc());
        assertThat(result.getDhu()).isNotEqualTo(categorySaved.getDhu());
        assertThat(result.getDhu()).isEqualTo(result.getDhu());
    }

}
