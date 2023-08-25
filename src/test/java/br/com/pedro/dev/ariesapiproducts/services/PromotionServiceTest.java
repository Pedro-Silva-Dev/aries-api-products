package br.com.pedro.dev.ariesapiproducts.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

import br.com.pedro.dev.ariesapiproducts.models.Promotion;
import br.com.pedro.dev.ariesapiproducts.models.requests.PromotionRequest;
import br.com.pedro.dev.ariesapiproducts.repositories.PromotionRepository;
import br.com.pedro.dev.ariesapiproducts.utils.PromotionCreateTest;

@DisplayName("Testar o service de Promocao.")
@ExtendWith(SpringExtension.class)
public class PromotionServiceTest {

    @InjectMocks
    private PromotionService promotionService;

    @Mock
    private PromotionRepository promotionRepositoryMock;

    @BeforeEach
    void setup() {
        PageImpl<Promotion> pageable = new PageImpl<>(List.of(PromotionCreateTest.getPromotionValid(1L)));

        BDDMockito.when(promotionRepositoryMock.findAll(ArgumentMatchers.any(BooleanBuilder.class), ArgumentMatchers.any(Pageable.class))).thenReturn(pageable);
        BDDMockito.when(promotionRepositoryMock.findAll(ArgumentMatchers.any(BooleanBuilder.class))).thenReturn(List.of(PromotionCreateTest.getPromotionValid(1L)));
        BDDMockito.when(promotionRepositoryMock.findById(1L)).thenReturn(Optional.of(PromotionCreateTest.getPromotionValid(1L)));
        BDDMockito.when(promotionRepositoryMock.save(ArgumentMatchers.any())).thenReturn(PromotionCreateTest.getPromotionValid(1L));
    }

    @Test
    @DisplayName("Obter uma pagina de promocao")
    void getPromotionPageTest() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Promotion> result = promotionService.getPagePromotion(null, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getTotalElements()).isPositive();
        assertThat(result.getContent().get(0)).isNotNull();
        assertThat(result.getContent().get(0).getId()).isPositive();
        assertThat(result.getContent().get(0).getName()).isNotNull();
        assertThat(result.getContent().get(0).getDescription()).isNotNull();
        assertThat(result.getContent().get(0).getDiscount()).isNotNull();
        assertThat(result.getContent().get(0).getDiscountPercentage()).isNotNull();
        assertThat(result.getContent().get(0).getDhi()).isNotNull();
        assertThat(result.getContent().get(0).getDhf()).isNotNull();
    }

    @Test
    @DisplayName("Obter uma lista de promocao")
    void getPromotionListTest() {
        List<Promotion> result = promotionService.getListPromotion(null);

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getId()).isPositive();
        assertThat(result.get(0).getName()).isNotNull();
        assertThat(result.get(0).getDescription()).isNotNull();
        assertThat(result.get(0).getDiscount()).isNotNull();
        assertThat(result.get(0).getDiscountPercentage()).isNotNull();
        assertThat(result.get(0).getDhi()).isNotNull();
        assertThat(result.get(0).getDhf()).isNotNull();
    }


    @Test
    @DisplayName("Obter um promocao por id")
    void getPromotionByIdTest() {
        Promotion result = promotionService.getPromotionById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isPositive();
        assertThat(result.getName()).isNotNull();
        assertThat(result.getDescription()).isNotNull();
        assertThat(result.getDiscount()).isNotNull();
        assertThat(result.getDiscountPercentage()).isNotNull();
        assertThat(result.getDhi()).isNotNull();
        assertThat(result.getDhf()).isNotNull();
    }

    @Test
    @DisplayName("Cadastrar uma promocao")
    void createPromotiontTest() {
        PromotionRequest promotiontRequest = PromotionRequest.builder()
        .setName("Novidades")
        .setDescription("Descricao")
        .setDiscount(new BigDecimal(2.00))
        .setDiscountPercentage(true)
        .setDhi(LocalDateTime.now())
        .setDhf(LocalDateTime.now())
        .setActive(true)
        .build();

        Promotion result = promotionService.createPromotion(promotiontRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isPositive();
        assertThat(result.getName()).isNotNull();
        assertThat(result.getDescription()).isNotNull();
        assertThat(result.getDiscount()).isNotNull();
        assertThat(result.getDiscountPercentage()).isNotNull();
        assertThat(result.getDhi()).isNotNull();
        assertThat(result.getDhf()).isNotNull();
    }

    @Test
    @DisplayName("Atualizar uma promocao")
    void updatePromotionTest() {
        PromotionRequest promotiontRequest = PromotionRequest.builder()
        .setName("Novidades")
        .setDescription("Descricao")
        .setDiscount(new BigDecimal(2.00))
        .setDiscountPercentage(true)
        .setDhi(LocalDateTime.now())
        .setDhf(LocalDateTime.now())
        .setActive(true)
        .build();

        Promotion result = promotionService.updatePromotion(1l, promotiontRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isPositive();
        assertThat(result.getName()).isNotNull();
        assertThat(result.getDescription()).isNotNull();
        assertThat(result.getDiscount()).isNotNull();
        assertThat(result.getDiscountPercentage()).isNotNull();
        assertThat(result.getDhi()).isNotNull();
        assertThat(result.getDhf()).isNotNull();
    }

}
