package br.com.pedro.dev.ariesapiproducts.models.params;

import java.math.BigDecimal;

import lombok.Builder;

@Builder(setterPrefix = "set")
public record ProductParams(
    String name,
    BigDecimal minPrice,
    BigDecimal maxPrice,
    Integer stock,
    Boolean active,
    Long categoryId
) {
    
}
