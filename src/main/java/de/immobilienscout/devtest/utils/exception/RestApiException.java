package de.immobilienscout.devtest.utils.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;


public abstract class RestApiException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public RestApiException(final ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public abstract HttpStatus getStatus();
}