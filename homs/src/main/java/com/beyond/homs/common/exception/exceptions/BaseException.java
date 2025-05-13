package com.beyond.homs.common.exception.exceptions;

import com.beyond.homs.common.exception.messages.ExceptionMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
@RequiredArgsConstructor
public class BaseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4010119068958861531L;

    private final String type;
    private final HttpStatus status;
    private final String errorCode;

    public BaseException(ExceptionMessage message) {
        super(message.getMessage());
        this.type = message.name();
        this.status = message.getStatus();
        this.errorCode = message.getCode();
    }

    public BaseException(ExceptionMessage message, String detail) {
        super(message.getMessage() + " - " + detail);
        this.type = message.name();
        this.status = message.getStatus();
        this.errorCode = message.getCode();
    }
}
