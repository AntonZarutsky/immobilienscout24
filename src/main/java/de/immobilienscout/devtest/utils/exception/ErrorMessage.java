package de.immobilienscout.devtest.utils.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.annotation.concurrent.Immutable;
import lombok.Value;

@Value
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorMessage {

    private String message;

    private String details;

}
