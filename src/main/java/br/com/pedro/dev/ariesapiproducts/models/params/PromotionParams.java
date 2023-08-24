package br.com.pedro.dev.ariesapiproducts.models.params;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;

@Builder(setterPrefix = "set")
public record PromotionParams(
    String name,
    BigDecimal minDiscount,
    BigDecimal maxDiscount,
    Boolean discountPercentage,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Boolean active
) {
    
}
