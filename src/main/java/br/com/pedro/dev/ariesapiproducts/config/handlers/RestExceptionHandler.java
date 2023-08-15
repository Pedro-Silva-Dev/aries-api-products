package br.com.pedro.dev.ariesapiproducts.config.handlers;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.pedro.dev.ariesapiproducts.config.RequestExceptionDetails;
import br.com.pedro.dev.ariesapiproducts.config.ValidationExcpetionDetails;
import br.com.pedro.dev.ariesapiproducts.config.exceptions.ValidationDetails;
import br.com.pedro.dev.ariesapiproducts.config.requests.BadRequestException;
import br.com.pedro.dev.ariesapiproducts.config.requests.NotAcceptableRequestException;
import br.com.pedro.dev.ariesapiproducts.config.requests.NotFoundRequestException;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<RequestExceptionDetails> handlerBadRequestException(BadRequestException exception){
        return new ResponseEntity<>(
                RequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Check the Documentation")
                        .details(exception.getMessage())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundRequestException.class)
    public ResponseEntity<RequestExceptionDetails> handlerNotFoundRequestException(NotFoundRequestException exception){
        return new ResponseEntity<>(
                RequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .title("Not Found Request Exception, Check the Documentation")
                        .details(exception.getMessage())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAcceptableRequestException.class)
    public ResponseEntity<RequestExceptionDetails> handlerNotAceptableRequestException(NotAcceptableRequestException exception){
        return new ResponseEntity<>(
                RequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_ACCEPTABLE.value())
                        .title("Not Acceptable Request Exception, Check the Documentation")
                        .details(exception.getMessage())
                        .build(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExcpetionDetails> handlerNotAceptableRequestException(MethodArgumentNotValidException exception){
        List<ValidationDetails> fieldErrors = exception.getBindingResult().getFieldErrors().stream().map(r -> new ValidationDetails(r.getField(), r.getDefaultMessage())).toList();

        return new ResponseEntity<>(
                ValidationExcpetionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Check the Documentation")
                        .details("Check field errors.")
                        .fieldErrors(fieldErrors)
                        .build(), HttpStatus.BAD_REQUEST);
    }

}