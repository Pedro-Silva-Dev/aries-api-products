package br.com.pedro.dev.ariesapiproducts.models.responses;

import java.math.BigDecimal;
import lombok.Builder;

@Builder(setterPrefix = "set")
public record ProductResponse(
    Long id,
    String name,
    String description,
    BigDecimal price,
    Integer stock,
    Boolean active,
    Integer categoryId
) {
    
}
