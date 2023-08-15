package br.com.pedro.dev.ariesapiproducts.config.exceptions;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ExcpetionDetails implements Serializable {
    protected String title;
    protected Integer status;
    protected String details;
    protected LocalDateTime timestamp;
}

