package com.example.crud;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GException extends RuntimeException{
    private String message;
    /*public GoalsNotFoundException(String exception){
        super(exception);
    }*/
    public GException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}



