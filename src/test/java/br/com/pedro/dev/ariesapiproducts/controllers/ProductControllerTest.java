package br.com.pedro.dev.ariesapiproducts.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.springframework.http.*;

import br.com.pedro.dev.ariesapiproducts.models.Category;
import br.com.pedro.dev.ariesapiproducts.models.Product;
import br.com.pedro.dev.ariesapiproducts.models.requests.CategoryRequest;
import br.com.pedro.dev.ariesapiproducts.models.requests.ProductRequest;
import br.com.pedro.dev.ariesapiproducts.models.responses.ProductResponse;
import br.com.pedro.dev.ariesapiproducts.repositories.CategoryRepository;
import br.com.pedro.dev.ariesapiproducts.repositories.ProductRepository;
import br.com.pedro.dev.ariesapiproducts.utils.CategoryCreateTest;
import br.com.pedro.dev.ariesapiproducts.utils.PageableResponse;
import br.com.pedro.dev.ariesapiproducts.utils.ProductCreateTest;

@Testcontainers
@DisplayName("Testar o controller de Produto.")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

     @Autowired
    private TestRestTemplate testRestTemplate;

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

    @DisplayName("Criar um produto antes de cada teste.")
    @BeforeEach
    void createAddress() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);
        var product = ProductCreateTest.getCreateProduct(categorySaved.getId());
        productRepository.save(product);
    }

    @DisplayName("Remover todos os produtos depois de cada teste.")
    @AfterEach
    void removeAfterEach() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Obter pagina de produtos.")
    void getProductPageTest() {
        String url = String.format("/products/page.json");
        ResponseEntity<PageableResponse<ProductResponse>> result = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<ProductResponse>>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isNotEmpty();
        assertThat(result.getBody().getContent().get(0).id()).isPositive();
        assertThat(result.getBody().getContent().get(0).name()).isNotNull();
        assertThat(result.getBody().getContent().get(0).description()).isNotNull();
        assertThat(result.getBody().getContent().get(0).price()).isNotNull();
        assertThat(result.getBody().getContent().get(0).stock()).isNotNull();
        assertThat(result.getBody().getContent().get(0).categoryId()).isNotNull();
        assertThat(result.getBody().getContent().get(0).active()).isNotNull();
    }

    @Test
    @DisplayName("Obter pagina de produtos filtrando por status.")
    void getProductFilterByStatusPageTest() {
        String url = String.format("/products/page.json?active=true");
        ResponseEntity<PageableResponse<ProductResponse>> result = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<ProductResponse>>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isNotEmpty();
        assertThat(result.getBody().getContent().get(0).id()).isPositive();
        assertThat(result.getBody().getContent().get(0).name()).isNotNull();
        assertThat(result.getBody().getContent().get(0).description()).isNotNull();
        assertThat(result.getBody().getContent().get(0).price()).isNotNull();
        assertThat(result.getBody().getContent().get(0).stock()).isNotNull();
        assertThat(result.getBody().getContent().get(0).categoryId()).isNotNull();
        assertThat(result.getBody().getContent().get(0).active()).isTrue();
    }

    @Test
    @DisplayName("Obter pagina de produtos filtrando por paramentros.")
    void getProductFilterByAllParamsPageTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);
        var product = ProductCreateTest.getCreateProduct(categorySaved.getId());
        Product productSaved = productRepository.save(product);

        String url = String.format("/products/page.json?active=%s&name=%s&minPrice=%s&maxPrince=%s&stock=%s&categoryId=%s", productSaved.getActive(), productSaved.getName(), 0, productSaved.getPrice(), productSaved.getStock(), productSaved.getCategoryId());
        ResponseEntity<PageableResponse<ProductResponse>> result = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<ProductResponse>>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isNotEmpty();
        assertThat(result.getBody().getContent().get(0).id()).isPositive();
        assertThat(result.getBody().getContent().get(0).name()).isNotNull();
        assertThat(result.getBody().getContent().get(0).description()).isNotNull();
        assertThat(result.getBody().getContent().get(0).price()).isNotNull();
        assertThat(result.getBody().getContent().get(0).stock()).isNotNull();
        assertThat(result.getBody().getContent().get(0).categoryId()).isNotNull();
        assertThat(result.getBody().getContent().get(0).active()).isTrue();
    }

    @Test
    @DisplayName("Obter pagina vazia de produtos.")
    void getProductEmptyPageTest() {
        productRepository.deleteAll();
        String url = String.format("/products/page.json");
        ResponseEntity<PageableResponse<ProductResponse>> result = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<ProductResponse>>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEmpty();
    }

    @Test
    @DisplayName("Obter lista de produtos.")
    public void getProductListTest() {
        String url = String.format("/products/list.json");
        ResponseEntity<List<ProductResponse>> result = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductResponse>>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().get(0).id()).isPositive();
        assertThat(result.getBody().get(0).name()).isNotNull();
        assertThat(result.getBody().get(0).description()).isNotNull();
        assertThat(result.getBody().get(0).price()).isNotNull();
        assertThat(result.getBody().get(0).stock()).isNotNull();
        assertThat(result.getBody().get(0).categoryId()).isNotNull();
        assertThat(result.getBody().get(0).active()).isTrue();
    }

    @Test
    @DisplayName("Obter lista de produtos filtrando por nome.")
    public void getProductFilterByNameListTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);
        var product = ProductCreateTest.getCreateProduct(categorySaved.getId());
        Product productSaved = productRepository.save(product);

        String url = String.format("/products/list.json?name=%s", productSaved.getName());
        ResponseEntity<List<ProductResponse>> result = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductResponse>>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).isNotEmpty();
        assertThat(result.getBody().get(0).id()).isPositive();
        assertThat(result.getBody().get(0).name()).isNotNull();
        assertThat(result.getBody().get(0).description()).isNotNull();
        assertThat(result.getBody().get(0).price()).isNotNull();
        assertThat(result.getBody().get(0).stock()).isNotNull();
        assertThat(result.getBody().get(0).categoryId()).isNotNull();
        assertThat(result.getBody().get(0).active()).isTrue();
    }

    @Test
    @DisplayName("Obter lista vazia de produtos.")
    public void getProductEmptyListTest() {
        productRepository.deleteAll();
        String url = String.format("/products/list.json");
        ResponseEntity<List<ProductResponse>> result = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductResponse>>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).isEmpty();
    }

    @Test
    @DisplayName("Obter uma produto por id.")
    public void getProductByIdTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);
        var product = ProductCreateTest.getCreateProduct(categorySaved.getId());
        Product productSaved = productRepository.save(product);

        String url = String.format("/products/%s.json", productSaved.getId());
        ResponseEntity<ProductResponse> result = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<ProductResponse>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isPositive();
        assertThat(result.getBody().name()).isNotNull();
        assertThat(result.getBody().description()).isNotNull();
        assertThat(result.getBody().price()).isNotNull();
        assertThat(result.getBody().stock()).isNotNull();
        assertThat(result.getBody().categoryId()).isNotNull();
        assertThat(result.getBody().active()).isTrue();
    }

    @Test
    @DisplayName("Obter um produto por id invalido.")
    public void getCategoryEmptyByIdTest() {
        String url = String.format("/products/%s.json", 0L);
        ResponseEntity<ProductResponse> result = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<ProductResponse>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Cadastrar um produto")
    public void createProductTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);

        String url = String.format("/products/create.json");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ProductRequest productRequest = ProductRequest.builder()
                                            .setName("Novidades")
                                            .setDescription("Produto Novo")
                                            .setPrice(new BigDecimal(1.49))
                                            .setStock(10)
                                            .setCategoryId(categorySaved.getId())
                                            .setActive(true)
                                            .build();

        HttpEntity<ProductRequest> requestEntity = new HttpEntity<>(productRequest, headers);
        ResponseEntity<ProductResponse> result = testRestTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<ProductResponse>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isPositive();
        assertThat(result.getBody().name()).isNotNull();
        assertThat(result.getBody().description()).isNotNull();
        assertThat(result.getBody().price()).isNotNull();
        assertThat(result.getBody().stock()).isNotNull();
        assertThat(result.getBody().categoryId()).isNotNull();
        assertThat(result.getBody().active()).isTrue();
    }

    @Test
    @DisplayName("Cadastrar um produto invalida")
    public void createProductInvalidTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);

        String url = String.format("/products/create.json");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ProductRequest productRequest = ProductRequest.builder()
                                            .setName("Novidades")
                                            .setDescription("Produto Novo")
                                            .setPrice(null)
                                            .setStock(null)
                                            .setCategoryId(categorySaved.getId())
                                            .setActive(null)
                                            .build();

        HttpEntity<ProductRequest> requestEntity = new HttpEntity<>(productRequest, headers);
        ResponseEntity<ProductResponse> result = testRestTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<ProductResponse>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Atualizar um produto")
    public void updateProductTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);
        var product = ProductCreateTest.getCreateProduct(categorySaved.getId());
        Product productSaved = productRepository.save(product);

        String url = String.format("/products/%s.json", productSaved.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ProductRequest productRequest = ProductRequest.builder()
                                            .setName("Novidades")
                                            .setDescription("Produto Novo")
                                            .setPrice(new BigDecimal(1.49))
                                            .setStock(10)
                                            .setCategoryId(categorySaved.getId())
                                            .setActive(true)
                                            .build();

        HttpEntity<ProductRequest> requestEntity = new HttpEntity<>(productRequest, headers);
        ResponseEntity<ProductResponse> result = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<ProductResponse>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isPositive();
        assertThat(result.getBody().name()).isNotNull();
        assertThat(result.getBody().description()).isNotNull();
        assertThat(result.getBody().price()).isNotNull();
        assertThat(result.getBody().stock()).isNotNull();
        assertThat(result.getBody().categoryId()).isNotNull();
        assertThat(result.getBody().active()).isTrue();
    }

    @Test
    @DisplayName("Atualizar um produto invalida")
    public void updateCategoryInvalidTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);
        var product = ProductCreateTest.getCreateProduct(categorySaved.getId());
        Product productSaved = productRepository.save(product);

        String url = String.format("/products/%s.json", productSaved.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ProductRequest productRequest = ProductRequest.builder()
                                            .setName("Novidades")
                                            .setDescription("Produto Novo")
                                            .setPrice(new BigDecimal(1.49))
                                            .setStock(10)
                                            .setCategoryId(categorySaved.getId())
                                            .setActive(null)
                                            .build();

        HttpEntity<ProductRequest> requestEntity = new HttpEntity<>(productRequest, headers);
        ResponseEntity<ProductResponse> result = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<ProductResponse>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Atualizar um produto que não existe")
    public void updateProductNotFoundTest() {
        Category category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);
        var product = ProductCreateTest.getCreateProduct(categorySaved.getId());
        Product productSaved = productRepository.save(product);

        String url = String.format("/products/%s.json", 0L);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ProductRequest productRequest = ProductRequest.builder()
                                            .setName("Novidades")
                                            .setDescription("Produto Novo")
                                            .setPrice(new BigDecimal(1.49))
                                            .setStock(10)
                                            .setCategoryId(categorySaved.getId())
                                            .setActive(true)
                                            .build();

        HttpEntity<ProductRequest> requestEntity = new HttpEntity<>(productRequest, headers);
        ResponseEntity<ProductResponse> result = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<ProductResponse>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


}
