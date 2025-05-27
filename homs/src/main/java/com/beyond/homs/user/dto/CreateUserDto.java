package com.beyond.homs.user.dto;

import com.beyond.homs.user.data.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateUserDto(
        @NotBlank String userName,
        @NotBlank String password,
        @NotBlank String managerName,
        @NotBlank String managerEmail,
        @NotBlank String managerPhone,
        @NotNull  UserRole role,
        @Positive Long companyId,
        @Positive Long deptId
) { }
