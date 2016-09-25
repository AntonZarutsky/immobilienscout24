package de.immobilienscout.devtest.utils.exception;

import org.springframework.http.HttpStatus;


public class BadRequestException extends RestApiException {

    public BadRequestException(String message, String description) {
        super(new ErrorMessage(message, description));
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}


