package br.com.pedro.dev.ariesapiproducts.models.requests;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(setterPrefix = "set")
public record ProductRequest(
    @NotNull @NotEmpty @NotBlank @Length(max = 300) String name,
    @Length(max = 500) String description,
    @NotNull BigDecimal price,
    @NotNull Integer stock,
    @NotNull Boolean active,
    @NotNull Long categoryId
) {
    
}
