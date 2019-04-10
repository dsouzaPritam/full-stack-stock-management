package com.assignment.stocksmanagementservices.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private String description;


    /**
     * Create an exceptionResponse
     * @param message
     * @param description
     * @return
     */
    public static ExceptionResponse of(final String message,
                                       String description){
        return new ExceptionResponse(new Date(), message, description);
    }

}
