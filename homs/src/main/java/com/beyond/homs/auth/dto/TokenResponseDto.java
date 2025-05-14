package com.beyond.homs.auth.dto;

public record TokenResponseDto(
        String accessToken,
        String refreshToken
) { }
