package br.com.pedro.dev.ariesapiproducts.models.requests;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(setterPrefix = "set")
public record PromotionRequest(
    @NotNull @NotEmpty @NotBlank @Length(max = 300) String name,
    @Length(max = 500) String description,
    @NotNull BigDecimal discount,
    @NotNull Boolean discountPercentage,
    @NotNull Boolean active,
    @NotNull LocalDateTime dhi,
    @NotNull LocalDateTime dhf
) {
    
}
