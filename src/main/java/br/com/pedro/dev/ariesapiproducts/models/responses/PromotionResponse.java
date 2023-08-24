package br.com.pedro.dev.ariesapiproducts.models.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;

@Builder(setterPrefix = "set")
public record PromotionResponse(
    Long id,
    String name,
    String description,
    BigDecimal discount,
    Boolean discountPercentage,
    Boolean active,
    LocalDateTime dhi,
    LocalDateTime dhf
) {
    
}
