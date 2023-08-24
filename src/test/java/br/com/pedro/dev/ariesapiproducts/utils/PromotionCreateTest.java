package br.com.pedro.dev.ariesapiproducts.utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.pedro.dev.ariesapiproducts.models.Promotion;

public class PromotionCreateTest {

    public static Promotion getCreatePromotion() {
        return Promotion.builder()
        .setName("Novidades")
        .setDescription("Descricao")
        .setDiscount(new BigDecimal(2.00))
        .setDiscountPercentage(true)
        .setDhi(LocalDateTime.now())
        .setDhf(LocalDateTime.now())
        .setActive(true)
        .build();
    }

    public static Promotion getUpdatePromotion(Long id) {
        return Promotion.builder()
        .setId(id)
        .setName("Novidades")
        .setDescription("Descricao")
        .setDiscount(new BigDecimal(2.00))
        .setDiscountPercentage(true)
        .setDhi(LocalDateTime.now())
        .setDhf(LocalDateTime.now())
        .setActive(true)
        .build();
    }

    public static Promotion getPromotionValid(Long id) {
        return Promotion.builder()
        .setId(id)
        .setName("Novidades")
        .setDescription("Descricao")
        .setDiscount(new BigDecimal(2.00))
        .setDiscountPercentage(true)
        .setDhi(LocalDateTime.now())
        .setDhf(LocalDateTime.now())
        .setActive(true)
        .build();
    }
}
