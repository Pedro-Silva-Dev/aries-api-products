package br.com.pedro.dev.ariesapiproducts.controllers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import br.com.pedro.dev.ariesapiproducts.config.RequestExceptionDetails;
import br.com.pedro.dev.ariesapiproducts.config.requests.NotFoundRequestException;
import br.com.pedro.dev.ariesapiproducts.models.Category;
import br.com.pedro.dev.ariesapiproducts.models.requests.CategoryRequest;
import br.com.pedro.dev.ariesapiproducts.models.responses.CategoryResponse;
import br.com.pedro.dev.ariesapiproducts.repositories.CategoryRepository;
import br.com.pedro.dev.ariesapiproducts.utils.CategoryCreateTest;
import br.com.pedro.dev.ariesapiproducts.utils.PageableResponse;

@Testcontainers
@DisplayName("Testar o controller de Endereco.")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

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

    @DisplayName("Criar uma categoria antes de cada teste.")
    @BeforeEach
    void createAddress() {
       var category = CategoryCreateTest.getCreateCategory();
       categoryRepository.save(category);
    }

    @DisplayName("Remover todas os categorias depois de cada teste.")
    @AfterEach
    void removeAfterEach() {
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("Obter pagina de categorias.")
    void getCategoryPageTest() {
        String url = String.format("/categories/page.json");
        ResponseEntity<PageableResponse<CategoryResponse>> result = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<CategoryResponse>>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isNotEmpty();
        assertThat(result.getBody().getContent().get(0).id()).isPositive();
        assertThat(result.getBody().getContent().get(0).name()).isNotNull();
        assertThat(result.getBody().getContent().get(0).active()).isNotNull();
    }

    @Test
    @DisplayName("Obter pagina de categorias filtrando por status.")
    void getCategoryFilterByStatusPageTest() {
        String url = String.format("/categories/page.json?active=true");
        ResponseEntity<PageableResponse<CategoryResponse>> result = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<CategoryResponse>>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isNotEmpty();
        assertThat(result.getBody().getContent().get(0).id()).isPositive();
        assertThat(result.getBody().getContent().get(0).name()).isNotNull();
        assertThat(result.getBody().getContent().get(0).active()).isTrue();
    }

    @Test
    @DisplayName("Obter pagina vazia de categorias.")
    void getCategoryEmptyPageTest() {
        categoryRepository.deleteAll();
        String url = String.format("/categories/page.json");
        ResponseEntity<PageableResponse<CategoryResponse>> result = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<CategoryResponse>>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEmpty();
    }

    @Test
    @DisplayName("Obter lista de categorias.")
    public void getCategoryListTest() {
        String url = String.format("/categories/list.json");
        ResponseEntity<List<CategoryResponse>> result = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CategoryResponse>>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).isNotEmpty();
        assertThat(result.getBody().get(0).id()).isPositive();
        assertThat(result.getBody().get(0).name()).isNotNull();
        assertThat(result.getBody().get(0).active()).isTrue();
    }

    @Test
    @DisplayName("Obter lista de categorias filtrando por nome.")
    public void getCategoryFilterByNameListTest() {
        var category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);

        String url = String.format("/categories/list.json?name=%s", categorySaved.getName());
        ResponseEntity<List<CategoryResponse>> result = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CategoryResponse>>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).isNotEmpty();
        assertThat(result.getBody().get(0).id()).isPositive();
        assertThat(result.getBody().get(0).name()).isNotNull();
        assertThat(result.getBody().get(0).name()).isEqualTo(categorySaved.getName());
        assertThat(result.getBody().get(0).active()).isTrue();
    }

    @Test
    @DisplayName("Obter lista vazia de categorias.")
    public void getCategoryEmptyListTest() {
        categoryRepository.deleteAll();
        String url = String.format("/categories/list.json");
        ResponseEntity<List<CategoryResponse>> result = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CategoryResponse>>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).isEmpty();
    }

    @Test
    @DisplayName("Obter uma categoria por id.")
    public void getCategoryByIdTest() {
        var category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);

        String url = String.format("/categories/%s.json", categorySaved.getId());
        ResponseEntity<CategoryResponse> result = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<CategoryResponse>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isPositive();
        assertThat(result.getBody().name()).isNotNull();
        assertThat(result.getBody().name()).isEqualTo(categorySaved.getName());
        assertThat(result.getBody().active()).isTrue();
    }

    @Test
    @DisplayName("Obter uma categoria por id invalido.")
    public void getCategoryEmptyByIdTest() {
        String url = String.format("/categories/%s.json", 0L);
        ResponseEntity<CategoryResponse> result = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<CategoryResponse>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Cadastrar uma categoria")
    public void createCategoryTest() {
        String url = String.format("/categories/create.json");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CategoryRequest categoryRequest = CategoryRequest.builder()
                                            .setName("Categoria Teste")
                                            .setActive(true)
                                            .build();

        HttpEntity<CategoryRequest> requestEntity = new HttpEntity<>(categoryRequest, headers);
        ResponseEntity<CategoryResponse> result = testRestTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<CategoryResponse>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isPositive();
        assertThat(result.getBody().name()).isNotNull();
        assertThat(result.getBody().name()).isEqualTo(categoryRequest.name());
        assertThat(result.getBody().active()).isEqualTo(categoryRequest.active());
    }

    @Test
    @DisplayName("Cadastrar uma categoria invalida")
    public void createCategoryInvalidTest() {
        String url = String.format("/categories/create.json");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CategoryRequest categoryRequest = CategoryRequest.builder()
                                            .setName("Categoria Teste")
                                            .build();

        HttpEntity<CategoryRequest> requestEntity = new HttpEntity<>(categoryRequest, headers);
        ResponseEntity<CategoryResponse> result = testRestTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<CategoryResponse>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Atualizar uma categoria")
    public void updateCategoryTest() {
        var category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);

        String url = String.format("/categories/%s.json", categorySaved.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CategoryRequest categoryRequest = CategoryRequest.builder()
                                            .setName("Categoria Atualizada")
                                            .setActive(true)
                                            .build();

        HttpEntity<CategoryRequest> requestEntity = new HttpEntity<>(categoryRequest, headers);
        ResponseEntity<CategoryResponse> result = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<CategoryResponse>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isPositive();
        assertThat(result.getBody().name()).isNotNull();
        assertThat(result.getBody().name()).isEqualTo(categoryRequest.name());
        assertThat(result.getBody().active()).isEqualTo(categoryRequest.active());
    }

    @Test
    @DisplayName("Atualizar uma categoria invalida")
    public void updateCategoryInvalidTest() {
        var category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);

        String url = String.format("/categories/%s.json", categorySaved.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CategoryRequest categoryRequest = CategoryRequest.builder()
                                            .setActive(true)
                                            .build();

        HttpEntity<CategoryRequest> requestEntity = new HttpEntity<>(categoryRequest, headers);
        ResponseEntity<CategoryResponse> result = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<CategoryResponse>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Atualizar uma categoria que não existe")
    public void updateCategoryNotFoundTest() {
        var category = CategoryCreateTest.getCreateCategory();
        Category categorySaved = categoryRepository.save(category);

        String url = String.format("/categories/%s.json", 0L);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CategoryRequest categoryRequest = CategoryRequest.builder()
                                            .setName("Categoria Atualizada")
                                            .setActive(true)
                                            .build();

        HttpEntity<CategoryRequest> requestEntity = new HttpEntity<>(categoryRequest, headers);
        ResponseEntity<CategoryResponse> result = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<CategoryResponse>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }




}
