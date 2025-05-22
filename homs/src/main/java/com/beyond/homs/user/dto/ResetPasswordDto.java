package com.beyond.homs.user.dto;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordDto(
        @NotBlank String userName
) { }
