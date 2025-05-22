package com.beyond.homs.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record SignInRequestDto(
        @NotBlank String userName,
        @NotBlank String password
) { }
