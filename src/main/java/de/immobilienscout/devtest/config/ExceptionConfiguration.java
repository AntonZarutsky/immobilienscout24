package de.immobilienscout.devtest.config;

import de.immobilienscout.devtest.utils.exception.ErrorMessage;
import de.immobilienscout.devtest.utils.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
@ControllerAdvice
/**
 * Custom configuration for exception handling.
 */
public class ExceptionConfiguration {

    private static final String API_PROBLEM_MEDIA_V2_TYPE = "application/problem+json";

    @ExceptionHandler(RestApiException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> restApiHandler(final RestApiException exception) {
        return respondWith(exception.getErrorMessage(), exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> defaultHandler(final Exception exception) {
        log.error("Error during request processing.", exception);

        return respondWith(new ErrorMessage("Internal Server Error", exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorMessage> respondWith(final ErrorMessage errorMessage, final HttpStatus status) {
        final HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_TYPE, API_PROBLEM_MEDIA_V2_TYPE);

        return new ResponseEntity(errorMessage, headers, status);
    }
}
