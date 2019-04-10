package com.assignment.stocksmanagementservices.exception;

import com.assignment.stocksmanagementservices.exception.model.ExceptionResponse;
import com.assignment.stocksmanagementservices.exception.model.StockNotFoundException;
import com.assignment.stocksmanagementservices.exception.model.StockServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * The class is used to send an customised error message body in case of exception in the rest services.
 * Created by Pritam Dsouza on 08/04/2019
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Handle all exceptions where unexpected error took place.
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(StockServiceException.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle exception in case stock is not found
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(StockNotFoundException.class)
    public final ResponseEntity<Object> handleStockNotFoundException(StockNotFoundException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle invalid request exception
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation Failed",
                ex.getBindingResult().toString());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
