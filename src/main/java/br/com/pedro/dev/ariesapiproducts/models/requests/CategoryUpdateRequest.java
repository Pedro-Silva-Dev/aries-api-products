package br.com.pedro.dev.ariesapiproducts.models.requests;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(setterPrefix = "set")
public record CategoryUpdateRequest(
    @NotNull Long id,
    @NotNull @NotEmpty @NotBlank @Length(max = 300) String name,
    @NotNull Boolean active
) {
    
}
