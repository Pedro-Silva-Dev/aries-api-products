package br.com.pedro.dev.ariesapiproducts.models.params;

import java.math.BigDecimal;

public record ProductParams(
    String name,
    BigDecimal minPrice,
    BigDecimal maxPrice,
    Integer stock,
    Boolean active,
    Long categoryId
) {
    
}
