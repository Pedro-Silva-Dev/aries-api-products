package br.com.pedro.dev.ariesapiproducts.config.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ValidationDetails {
    private String field;
    private String message;
}

