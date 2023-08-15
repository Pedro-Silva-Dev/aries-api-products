package br.com.pedro.dev.ariesapiproducts.config.requests;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptableRequestException extends RuntimeException {
    public NotAcceptableRequestException(String message) {
        super(message);
    }  
}
