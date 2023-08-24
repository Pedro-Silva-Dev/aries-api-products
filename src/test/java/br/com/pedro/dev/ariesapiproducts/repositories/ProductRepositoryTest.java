package br.com.pedro.dev.ariesapiproducts.repositories;

import static org.junit.Assert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
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
import br.com.pedro.dev.ariesapiproducts.models.Product;
import br.com.pedro.dev.ariesapiproducts.models.QProduct;
import br.com.pedro.dev.ariesapiproducts.utils.CategoryCreateTest;
import br.com.pedro.dev.ariesapiproducts.utils.ProductCreateTest;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Testcontainers
@DisplayName("Testar o repositorio de Produto.")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository; 

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

    @DisplayName("Remover todos os produtos depois de cada teste.")
    @AfterEach
    void removeAfterEach() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Obter uma pagina de todos os produtos")
    void getPageProductTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);

        Product product = ProductCreateTest.getCreateProduct(categorySaved.getId());
        productRepository.save(product);

        PageRequest pageable = PageRequest.of(0, 10);
        Page<Product> result = productRepository.findAll(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getTotalElements()).isPositive();
        assertThat(result.getSize()).isEqualTo(pageable.getPageSize());
        assertThat(result.getContent().get(0)).isNotNull();
        assertThat(result.getContent().get(0).getId()).isPositive();
        assertThat(result.getContent().get(0).getName()).isNotNull();
        assertThat(result.getContent().get(0).getDescription()).isNotNull();
        assertThat(result.getContent().get(0).getPrice()).isNotNull();
        assertThat(result.getContent().get(0).getStock()).isNotNull();
        assertThat(result.getContent().get(0).getCategoryId()).isNotNull();
        assertThat(result.getContent().get(0).getDhc()).isNotNull();
        assertThat(result.getContent().get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma pagina de todos os produtos filtrando por nome")
    void getPageProductFilterByNameTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);

        Product product = ProductCreateTest.getCreateProduct(categorySaved.getId());
        productRepository.save(product);

        PageRequest pageable = PageRequest.of(0, 10);
        QProduct qProduct = QProduct.product;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qProduct.name.eq(product.getName()));

        Page<Product> result = productRepository.findAll(builder, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getTotalElements()).isPositive();
        assertThat(result.getSize()).isEqualTo(pageable.getPageSize());
        assertThat(result.getContent().get(0)).isNotNull();
        assertThat(result.getContent().get(0).getId()).isPositive();
        assertThat(result.getContent().get(0).getName()).isNotNull();
        assertThat(result.getContent().get(0).getDescription()).isNotNull();
        assertThat(result.getContent().get(0).getPrice()).isNotNull();
        assertThat(result.getContent().get(0).getStock()).isNotNull();
        assertThat(result.getContent().get(0).getCategoryId()).isNotNull();
        assertThat(result.getContent().get(0).getDhc()).isNotNull();
        assertThat(result.getContent().get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma pagina de todos os produtos filtrando por atributos")
    void getPageProductFilterByNameAndStatusTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);

        Product product = ProductCreateTest.getCreateProduct(categorySaved.getId());
        productRepository.save(product);

        PageRequest pageable = PageRequest.of(0, 10);
        QProduct qProduct = QProduct.product;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qProduct.name.eq(product.getName()));
        builder.and(qProduct.price.between(0, 100));
        builder.and(qProduct.categoryId.eq(product.getCategoryId()));
        builder.and(qProduct.stock.goe(0));
        builder.and(qProduct.active.eq(product.getActive()));

        Page<Product> result = productRepository.findAll(builder, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getTotalElements()).isPositive();
        assertThat(result.getSize()).isEqualTo(pageable.getPageSize());
        assertThat(result.getContent().get(0)).isNotNull();
        assertThat(result.getContent().get(0).getId()).isPositive();
        assertThat(result.getContent().get(0).getName()).isNotNull();
        assertThat(result.getContent().get(0).getDescription()).isNotNull();
        assertThat(result.getContent().get(0).getPrice()).isNotNull();
        assertThat(result.getContent().get(0).getStock()).isNotNull();
        assertThat(result.getContent().get(0).getCategoryId()).isNotNull();
        assertThat(result.getContent().get(0).getDhc()).isNotNull();
        assertThat(result.getContent().get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma lista de todos os produtos")
    void getListProductTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);

        Product product = ProductCreateTest.getCreateProduct(categorySaved.getId());
        productRepository.save(product);

        List<Product> result = productRepository.findAll();

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getId()).isPositive();
        assertThat(result.get(0).getName()).isNotNull();
        assertThat(result.get(0).getDescription()).isNotNull();
        assertThat(result.get(0).getPrice()).isNotNull();
        assertThat(result.get(0).getStock()).isNotNull();
        assertThat(result.get(0).getCategoryId()).isNotNull();
        assertThat(result.get(0).getDhc()).isNotNull();
        assertThat(result.get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma lista de todos os produtos filtrando por nome")
    void getListProductFilterByNameTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);

        Product product = ProductCreateTest.getCreateProduct(categorySaved.getId());
        productRepository.save(product);

        QProduct qProduct = QProduct.product;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qProduct.name.eq(product.getName()));

        List<Product> result = (List<Product>) productRepository.findAll(builder);

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getId()).isPositive();
        assertThat(result.get(0).getName()).isNotNull();
        assertThat(result.get(0).getDescription()).isNotNull();
        assertThat(result.get(0).getPrice()).isNotNull();
        assertThat(result.get(0).getStock()).isNotNull();
        assertThat(result.get(0).getCategoryId()).isNotNull();
        assertThat(result.get(0).getDhc()).isNotNull();
        assertThat(result.get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma lista de todos os produtos filtrando por atributos")
    void getListProductFilterByNameAndStatusTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);

        Product product = ProductCreateTest.getCreateProduct(categorySaved.getId());
        productRepository.save(product);

        QProduct qProduct = QProduct.product;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qProduct.name.eq(product.getName()));
        builder.and(qProduct.price.between(0, 100));
        builder.and(qProduct.categoryId.eq(product.getCategoryId()));
        builder.and(qProduct.stock.goe(0));
        builder.and(qProduct.active.eq(product.getActive()));

        List<Product> result = (List<Product>) productRepository.findAll(builder);

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getId()).isPositive();
        assertThat(result.get(0).getName()).isNotNull();
        assertThat(result.get(0).getDescription()).isNotNull();
        assertThat(result.get(0).getPrice()).isNotNull();
        assertThat(result.get(0).getStock()).isNotNull();
        assertThat(result.get(0).getCategoryId()).isNotNull();
        assertThat(result.get(0).getDhc()).isNotNull();
        assertThat(result.get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter um produto por id")
    void getProductByIdTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);

        Product product = ProductCreateTest.getCreateProduct(categorySaved.getId());
        Product productSaved = productRepository.save(product);

        Optional<Product> result = productRepository.findById(productSaved.getId());

        assertThat(result).isNotNull();
        assertThat(result).isPresent();
        assertThat(result.get()).isNotNull();
        assertThat(result.get().getId()).isPositive();
        assertThat(result.get().getName()).isNotNull();
        assertThat(result.get().getDescription()).isNotNull();
        assertThat(result.get().getPrice()).isNotNull();
        assertThat(result.get().getStock()).isNotNull();
        assertThat(result.get().getCategoryId()).isNotNull();
        assertThat(result.get().getDhc()).isNotNull();
        assertThat(result.get().getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter um produto por id invalido")
    void getProductByIdInvalidTest() {
        Optional<Product> result = productRepository.findById(0L);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Atualizar um produto por id")
    void updateProductByIdTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category category2 = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);
        Category categorySaved2 = categoryRepository.save(category2);

        Product product = ProductCreateTest.getCreateProduct(categorySaved.getId());
        Product productSaved = productRepository.save(product);

        productSaved.setName("Nome atualizado");
        productSaved.setDescription("Descrição Atualizada");
        productSaved.setPrice(new BigDecimal(2.00));
        productSaved.setStock(20);
        productSaved.setActive(false);
        productSaved.setCategoryId(categorySaved2.getId());
        Product result = productRepository.save(productSaved);

        log.info("categorySaved: {}, categorySaved2: {}", categorySaved.getId(), categorySaved2.getId());

        assertThat(result).isNotNull();
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(productSaved.getId());
        assertThat(result.getName()).isEqualTo(productSaved.getName());
        assertThat(result.getDescription()).isEqualTo(productSaved.getDescription());
        assertThat(result.getPrice()).isEqualTo(productSaved.getPrice());
        assertThat(result.getStock()).isEqualTo(productSaved.getStock());
        assertThat(result.getCategoryId()).isNotEqualTo(categorySaved.getId());
        assertThat(result.getCategoryId()).isEqualTo(categorySaved2.getId());
        assertThat(result.getDhc()).isEqualTo(productSaved.getDhc());
        assertThat(result.getDhu()).isNotEqualTo(productSaved.getDhu());
    }


}
