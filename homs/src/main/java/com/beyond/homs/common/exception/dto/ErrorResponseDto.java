package com.beyond.homs.common.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {
    private final int status;

    private final String requestId;

    private final String message;

    private final LocalDateTime time;

    private String errorCode;

    private List<ValidationError> validErrors;

    @Data
    @RequiredArgsConstructor
    private static class ValidationError {
        private final String field;
        private final String message;
    }

}
