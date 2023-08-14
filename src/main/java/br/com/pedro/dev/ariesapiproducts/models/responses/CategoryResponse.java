package br.com.pedro.dev.ariesapiproducts.models.responses;

import lombok.Builder;

@Builder(setterPrefix = "set")
public record CategoryResponse(
    Long id,
    String name,
    Boolean active
) {
    
}
