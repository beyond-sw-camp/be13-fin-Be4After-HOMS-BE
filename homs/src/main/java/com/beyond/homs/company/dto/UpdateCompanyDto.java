package com.beyond.homs.company.dto;

import com.beyond.homs.company.data.CountryEnum;
import com.beyond.homs.company.entity.Country;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateCompanyDto(
        Country country,
        String companyName,
        String registrationNumber,
        String representName,
        String representCall,
        String representPhone,
        String representManagerName,
        String representManagerEmail,
        Boolean continueStatus,
        Boolean approveStatus
) {
}
