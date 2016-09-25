package de.immobilienscout.devtest.utils.exception;

import org.springframework.http.HttpStatus;


public class ProcessingException extends RuntimeException{

    public ProcessingException(String message) {
        super(message);
    }

    public ProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}


