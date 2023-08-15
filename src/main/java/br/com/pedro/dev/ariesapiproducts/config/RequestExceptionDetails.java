package br.com.pedro.dev.ariesapiproducts.config;

import java.io.Serializable;

import br.com.pedro.dev.ariesapiproducts.config.exceptions.ExcpetionDetails;
import lombok.Getter;
import lombok.experimental.SuperBuilder;



@Getter
@SuperBuilder
public class RequestExceptionDetails extends ExcpetionDetails implements Serializable {
    
}
