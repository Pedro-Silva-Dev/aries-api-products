package br.com.pedro.dev.ariesapiproducts.services;

import static org.junit.Assert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.querydsl.core.BooleanBuilder;

import br.com.pedro.dev.ariesapiproducts.models.Product;
import br.com.pedro.dev.ariesapiproducts.models.requests.ProductRequest;
import br.com.pedro.dev.ariesapiproducts.repositories.ProductRepository;
import br.com.pedro.dev.ariesapiproducts.utils.ProductCreateTest;

@DisplayName("Testar o service de Produto.")
@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepositoryMock;

    @BeforeEach
    void setup() {
        PageImpl<Product> pageable = new PageImpl<>(List.of(ProductCreateTest.getProductValid(1L, 1L)));

        BDDMockito.when(productRepositoryMock.findAll(ArgumentMatchers.any(BooleanBuilder.class), ArgumentMatchers.any(Pageable.class))).thenReturn(pageable);
        BDDMockito.when(productRepositoryMock.findAll(ArgumentMatchers.any(BooleanBuilder.class))).thenReturn(List.of(ProductCreateTest.getProductValid(1L, 1L)));
        BDDMockito.when(productRepositoryMock.findById(1L)).thenReturn(Optional.of(ProductCreateTest.getProductValid(1L, 1L)));
        BDDMockito.when(productRepositoryMock.save(ArgumentMatchers.any())).thenReturn(ProductCreateTest.getProductValid(1L, 1L));
    }

    @Test
    @DisplayName("Obter uma pagina de produto")
    void getProductPageTest() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Product> result = productService.getPageProduct(null, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getTotalElements()).isPositive();
        assertThat(result.getContent().get(0)).isNotNull();
        assertThat(result.getContent().get(0).getId()).isPositive();
        assertThat(result.getContent().get(0).getName()).isNotNull();
        assertThat(result.getContent().get(0).getDescription()).isNotNull();
        assertThat(result.getContent().get(0).getPrice()).isNotNull();
        assertThat(result.getContent().get(0).getStock()).isNotNull();
        assertThat(result.getContent().get(0).getCategoryId()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma lista de produto")
    void getProductListTest() {
        List<Product> result = productService.getListProduct(null);

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getId()).isPositive();
        assertThat(result.get(0).getName()).isNotNull();
        assertThat(result.get(0).getDescription()).isNotNull();
        assertThat(result.get(0).getPrice()).isNotNull();
        assertThat(result.get(0).getStock()).isNotNull();
        assertThat(result.get(0).getCategoryId()).isNotNull();
    }


    @Test
    @DisplayName("Obter um produto por id")
    void getProductByIdTest() {
        Product result = productService.getProductById(1L);

        assertThat(result).isNotNull();
        assertThat(result).isNotNull();
        assertThat(result.getId()).isPositive();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isNotNull();
        assertThat(result.getDescription()).isNotNull();
        assertThat(result.getPrice()).isNotNull();
        assertThat(result.getStock()).isNotNull();
        assertThat(result.getCategoryId()).isNotNull();
    }

    @Test
    @DisplayName("Cadastrar um produto")
    void createProductTest() {
        ProductRequest productRequest = ProductRequest.builder()
        .setName("Novidades")
        .setDescription("Produto Novo")
        .setPrice(new BigDecimal(1.49))
        .setStock(10)
        .setCategoryId(1L)
        .setActive(true)
        .build();

        Product result = productService.createProduct(productRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isPositive();
        assertThat(result.getName()).isNotNull();
        assertThat(result.getDescription()).isNotNull();
        assertThat(result.getPrice()).isNotNull();
        assertThat(result.getStock()).isNotNull();
        assertThat(result.getCategoryId()).isNotNull();
    }

    @Test
    @DisplayName("Atualizar um produto")
    void updateProductTest() {
        ProductRequest productRequest = ProductRequest.builder()
        .setName("Novidades Atualizado")
        .setDescription("Produto Atualizado")
        .setPrice(new BigDecimal(1.49))
        .setStock(10)
        .setCategoryId(1L)
        .setActive(true)
        .build();

        Product result = productService.createProduct(productRequest);

        assertThat(result).isNotNull();
        assertThat(result).isNotNull();
        assertThat(result.getId()).isPositive();
        assertThat(result.getName()).isNotNull();
        assertThat(result.getDescription()).isNotNull();
        assertThat(result.getPrice()).isNotNull();
        assertThat(result.getStock()).isNotNull();
        assertThat(result.getCategoryId()).isNotNull();
    }

}
