package com.projsalony.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.projsalony.domain.InternalCodeEnum;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseBody<T> {
    private boolean success;
    private String message;

    @JsonIgnore
    private InternalCodeEnum internalCode;
    private String timestamp;
    private final T data;


    public ApiResponseBody(boolean success, String message, InternalCodeEnum internalCode, String timestamp, T data) {
        this.success = success;
        this.message = message;
        this.internalCode = internalCode;
        this.timestamp = LocalDateTime.now().toString();
        this.data = data;
    }

    public ApiResponseBody(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.timestamp = ZonedDateTime.now(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
        this.data = data;
    }
}
