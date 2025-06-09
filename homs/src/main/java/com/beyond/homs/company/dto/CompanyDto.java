package com.beyond.homs.company.dto;

import com.beyond.homs.company.data.CountryEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompanyDto(
        @NotNull CountryEnum country,
        @NotBlank String companyName,
        @NotBlank String registrationNumber,
        @NotBlank String representName,
        @NotBlank String representCall,
        @NotBlank String representPhone,
        @NotBlank String representManagerName,
        @NotBlank @Email String representManagerEmail,
        Boolean continueStatus,  // <- 추가
        Boolean approveStatus
) {}