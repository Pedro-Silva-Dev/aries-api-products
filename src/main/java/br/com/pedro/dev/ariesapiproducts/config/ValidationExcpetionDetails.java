package br.com.pedro.dev.ariesapiproducts.config;

import java.io.Serializable;
import java.util.List;

import br.com.pedro.dev.ariesapiproducts.config.exceptions.ValidationDetails;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExcpetionDetails extends RequestExceptionDetails implements Serializable {
     private List<ValidationDetails> fieldErrors;
}
