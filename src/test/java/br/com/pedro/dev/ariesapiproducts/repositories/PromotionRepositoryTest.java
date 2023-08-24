package br.com.pedro.dev.ariesapiproducts.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import br.com.pedro.dev.ariesapiproducts.models.Promotion;
import br.com.pedro.dev.ariesapiproducts.models.QProduct;
import br.com.pedro.dev.ariesapiproducts.models.QPromotion;
import br.com.pedro.dev.ariesapiproducts.utils.PromotionCreateTest;
import br.com.pedro.dev.ariesapiproducts.utils.ProductCreateTest;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Testcontainers
@DisplayName("Testar o repositorio de Promocao.")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PromotionRepositoryTest {

    @Autowired
    private PromotionRepository promotionRepository;

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

    @DisplayName("Remover todoas as promocoes depois de cada teste.")
    @AfterEach
    void removeAfterEach() {
        promotionRepository.deleteAll();
    }

    @Test
    @DisplayName("Obter uma pagina de todas as promocoes")
    void getPagePromotionTest() {
        Promotion promotion = PromotionCreateTest.getCreatePromotion();
        Promotion promotionSaved = promotionRepository.save(promotion);

        PageRequest pageable = PageRequest.of(0, 10);
        Page<Promotion> result = promotionRepository.findAll(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getTotalElements()).isPositive();
        assertThat(result.getSize()).isEqualTo(pageable.getPageSize());
        assertThat(result.getContent().get(0)).isNotNull();
        assertThat(result.getContent().get(0).getId()).isPositive();
        assertThat(result.getContent().get(0).getName()).isNotNull();
        assertThat(result.getContent().get(0).getDescription()).isNotNull();
        assertThat(result.getContent().get(0).getDiscount()).isNotNull();
        assertThat(result.getContent().get(0).getDiscountPercentage()).isTrue();
        assertThat(result.getContent().get(0).getDhi()).isNotNull();
        assertThat(result.getContent().get(0).getDhf()).isNotNull();
        assertThat(result.getContent().get(0).getDhc()).isNotNull();
        assertThat(result.getContent().get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma pagina de todas as promocoes filtrando por nome")
    void getPagePromotionFilterByNameTest() {
        Promotion promotion = PromotionCreateTest.getCreatePromotion();
        Promotion promotionSaved = promotionRepository.save(promotion);

        PageRequest pageable = PageRequest.of(0, 10);
        QPromotion qPromotion = QPromotion.promotion;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qPromotion.name.eq(promotion.getName()));

        Page<Promotion> result = promotionRepository.findAll(builder, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getTotalElements()).isPositive();
        assertThat(result.getSize()).isEqualTo(pageable.getPageSize());
        assertThat(result.getContent().get(0)).isNotNull();
        assertThat(result.getContent().get(0).getId()).isPositive();
        assertThat(result.getContent().get(0).getName()).isNotNull();
        assertThat(result.getContent().get(0).getDescription()).isNotNull();
        assertThat(result.getContent().get(0).getDiscount()).isNotNull();
        assertThat(result.getContent().get(0).getDiscountPercentage()).isTrue();
        assertThat(result.getContent().get(0).getDhi()).isNotNull();
        assertThat(result.getContent().get(0).getDhf()).isNotNull();
        assertThat(result.getContent().get(0).getDhc()).isNotNull();
        assertThat(result.getContent().get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma pagina de todas as promocoes filtrando por atributos")
    void getPagePromotionFilterByNameAndStatusTest() {
        Promotion promotion = PromotionCreateTest.getCreatePromotion();
        Promotion promotionSaved = promotionRepository.save(promotion);

        PageRequest pageable = PageRequest.of(0, 10);
        QPromotion qPromotion = QPromotion.promotion;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qPromotion.name.eq(promotion.getName()));
        builder.and(qPromotion.discount.between(0, 100));
        builder.and(qPromotion.discountPercentage.eq(promotion.getDiscountPercentage()));
        builder.and(qPromotion.active.eq(promotion.getActive()));

        Page<Promotion> result = promotionRepository.findAll(builder, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getTotalElements()).isPositive();
        assertThat(result.getSize()).isEqualTo(pageable.getPageSize());
        assertThat(result.getContent().get(0)).isNotNull();
        assertThat(result.getContent().get(0).getId()).isPositive();
        assertThat(result.getContent().get(0).getName()).isNotNull();
        assertThat(result.getContent().get(0).getDescription()).isNotNull();
        assertThat(result.getContent().get(0).getDiscount()).isNotNull();
        assertThat(result.getContent().get(0).getDiscountPercentage()).isTrue();
        assertThat(result.getContent().get(0).getDhi()).isNotNull();
        assertThat(result.getContent().get(0).getDhf()).isNotNull();
        assertThat(result.getContent().get(0).getDhc()).isNotNull();
        assertThat(result.getContent().get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma lista de todas as promocoes")
    void getListPromotionTest() {
        Promotion promotion = PromotionCreateTest.getCreatePromotion();
        Promotion promotionSaved = promotionRepository.save(promotion);

        List<Promotion> result = promotionRepository.findAll();

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getId()).isPositive();
        assertThat(result.get(0).getName()).isNotNull();
        assertThat(result.get(0).getDescription()).isNotNull();
        assertThat(result.get(0).getDiscount()).isNotNull();
        assertThat(result.get(0).getDiscountPercentage()).isTrue();
        assertThat(result.get(0).getDhi()).isNotNull();
        assertThat(result.get(0).getDhf()).isNotNull();
        assertThat(result.get(0).getDhc()).isNotNull();
        assertThat(result.get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma lista de todas as promocoes filtrando por nome")
    void getListPromotionFilterByNameTest() {
        Promotion promotion = PromotionCreateTest.getCreatePromotion();
        Promotion promotionSaved = promotionRepository.save(promotion);

        QPromotion qPromotion = QPromotion.promotion;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qPromotion.name.eq(promotion.getName()));

        List<Promotion> result = (List<Promotion>) promotionRepository.findAll(builder);

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getId()).isPositive();
        assertThat(result.get(0).getName()).isNotNull();
        assertThat(result.get(0).getDescription()).isNotNull();
        assertThat(result.get(0).getDiscount()).isNotNull();
        assertThat(result.get(0).getDiscountPercentage()).isTrue();
        assertThat(result.get(0).getDhi()).isNotNull();
        assertThat(result.get(0).getDhf()).isNotNull();
        assertThat(result.get(0).getDhc()).isNotNull();
        assertThat(result.get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma lista de todas as promocoes filtrando por atributos")
    void getListPromotionFilterByParamsTest() {
        Promotion promotion = PromotionCreateTest.getCreatePromotion();
        Promotion promotionSaved = promotionRepository.save(promotion);

        QPromotion qPromotion = QPromotion.promotion;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qPromotion.name.eq(promotion.getName()));
        builder.and(qPromotion.discount.between(0, 100));
        builder.and(qPromotion.discountPercentage.eq(promotion.getDiscountPercentage()));
        builder.and(qPromotion.active.eq(promotion.getActive()));

        List<Promotion> result = (List<Promotion>) promotionRepository.findAll(builder);

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getId()).isPositive();
        assertThat(result.get(0).getName()).isNotNull();
        assertThat(result.get(0).getDescription()).isNotNull();
        assertThat(result.get(0).getDiscount()).isNotNull();
        assertThat(result.get(0).getDiscountPercentage()).isTrue();
        assertThat(result.get(0).getDhi()).isNotNull();
        assertThat(result.get(0).getDhf()).isNotNull();
        assertThat(result.get(0).getDhc()).isNotNull();
        assertThat(result.get(0).getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma promocao por id")
    void getPromotionByIdTest() {
        Promotion promotion = PromotionCreateTest.getCreatePromotion();
        Promotion promotionSaved = promotionRepository.save(promotion);

        Optional<Promotion> result = promotionRepository.findById(promotionSaved.getId());

        assertThat(result).isNotNull();
        assertThat(result).isPresent();
        assertThat(result.get()).isNotNull();
        assertThat(result.get().getId()).isPositive();
        assertThat(result.get().getName()).isNotNull();
        assertThat(result.get().getDescription()).isNotNull();
        assertThat(result.get().getDiscount()).isNotNull();
        assertThat(result.get().getDiscountPercentage()).isTrue();
        assertThat(result.get().getDhi()).isNotNull();
        assertThat(result.get().getDhf()).isNotNull();
        assertThat(result.get().getDhc()).isNotNull();
        assertThat(result.get().getDhu()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma promocao por id invalido")
    void getPromotionByIdInvalidTest() {
        Optional<Promotion> result = promotionRepository.findById(0L);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Atualizar uma promocao por id")
    void updateCategoryByIdTest() {
        Promotion promotion = PromotionCreateTest.getCreatePromotion();
        Promotion promotionSaved = promotionRepository.save(promotion);

        promotionSaved.setName("Nome atualizado");
        promotionSaved.setDescription("Descrição Atualizada");
        promotionSaved.setDiscount(new BigDecimal(2.00));
        promotionSaved.setDiscountPercentage(false);
        promotionSaved.setActive(false);
        promotionSaved.setDhf(LocalDateTime.now());
        Promotion result = promotionRepository.save(promotionSaved);

        assertThat(result).isNotNull();
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(promotionSaved.getId());
        assertThat(result.getName()).isEqualTo(promotionSaved.getName());
        assertThat(result.getDescription()).isEqualTo(promotionSaved.getDescription());
        assertThat(result.getDiscount()).isEqualTo(promotionSaved.getDiscount());
        assertThat(result.getDiscountPercentage()).isEqualTo(promotionSaved.getDiscountPercentage());
        assertThat(result.getDhc()).isEqualTo(promotionSaved.getDhc());
        assertThat(result.getDhu()).isNotEqualTo(promotionSaved.getDhu());
    }


}
