package org.my.controller;

import org.my.service.BAException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * Web layer exception handlers.
 * Error responses generation
 */
@ControllerAdvice
public class BAExceptionResolver extends ResponseEntityExceptionHandler {
    /**
     * BAException entity exception handler
     *
     * @param exception handled exception
     * @param request   web request
     * @return response
     */
    @ExceptionHandler(value = BAException.class)
    protected ResponseEntity<Object> handleUnprocessableEntityException(
            BAException exception,
            WebRequest request
    ) {
        return handleExceptionInternal(
                exception, exception.getMessage(), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request
        );
    }
}
