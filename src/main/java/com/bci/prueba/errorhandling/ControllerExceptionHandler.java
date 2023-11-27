package com.bci.prueba.errorhandling;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({GenericException.class})
    public final ResponseEntity<ErrorResponse> handleGenericExceptions(GenericException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(new ErrorResponse(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public final ErrorResponse handleNoSuchElementExceptions(NoSuchElementException ex) {
        log.error(ex.getMessage());
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleExceptions(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorResponse(ex.getMessage()));
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
        log.error(ex.getMessage());
        return new ErrorResponse(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ex.getMessage());
        var errors = ex.getBindingResult()
                .getFieldErrors().stream().map(fieldError ->
                        String.format("Error en el object '%s', campo '%s' con valor rechazado '%s' y la razón es '%s'.",
                                fieldError.getObjectName(), fieldError.getField(),
                                fieldError.getRejectedValue(), fieldError.getDefaultMessage()))
                .collect(Collectors.joining("; "));

        return ResponseEntity.status(BAD_REQUEST).body(new ErrorResponse(errors));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ex.getMessage());
        return ResponseEntity.status(status).body(new ErrorResponse(ex.getMessage()));
    }

}

