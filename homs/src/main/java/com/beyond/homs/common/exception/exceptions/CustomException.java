package com.beyond.homs.common.exception.exceptions;

import com.beyond.homs.common.exception.messages.ExceptionMessage;
import lombok.Getter;

import java.io.Serial;

@Getter
public class CustomException extends BaseException {
    @Serial
    private static final long serialVersionUID = 9027521527479426184L;

    public CustomException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage);

    }
}