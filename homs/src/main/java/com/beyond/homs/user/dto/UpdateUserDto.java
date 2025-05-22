package com.beyond.homs.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserDto(
        @NotBlank String managerName,
        @Email String managerEmail,
        @NotBlank String managerPhone
) { }
