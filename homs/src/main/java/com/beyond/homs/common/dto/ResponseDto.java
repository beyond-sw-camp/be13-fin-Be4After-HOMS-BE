package com.beyond.homs.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseDto<T> {
    private final int statusCode;
    private final String message;
    private final T data;
}
