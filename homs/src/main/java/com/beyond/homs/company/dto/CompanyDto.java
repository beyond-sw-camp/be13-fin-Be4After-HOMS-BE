package com.beyond.homs.company.dto;

import com.beyond.homs.company.data.CountryEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CompanyDto(
        @NotBlank CountryEnum country,
        @NotBlank String companyName,
        @NotBlank String registrationNumber,
        @NotBlank String representName,
        @NotBlank String representCall,
        @NotBlank String representPhone,
        @NotBlank String representManagerName,
        @NotBlank @Email String representManagerEmail
) {}