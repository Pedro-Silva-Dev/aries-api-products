package br.com.pedro.dev.ariesapiproducts.config.exceptions;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ValidationDetails implements Serializable {
    private String field;
    private String message;
}

