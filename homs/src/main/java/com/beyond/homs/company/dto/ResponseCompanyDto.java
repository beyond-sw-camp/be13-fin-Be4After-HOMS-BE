package com.beyond.homs.company.dto;

import com.beyond.homs.company.entity.Company;

import java.time.LocalDateTime;

public record ResponseCompanyDto(
        Long companyId,
        String companyName,
        String registrationNumber,
        String representName,
        String representCall,
        String representPhone,
        String representManagerName,
        String representManagerEmail,
        LocalDateTime createdAt,
        boolean continueStatus,
        boolean approvedStatus,
        Long countryId
) {
    public static ResponseCompanyDto fromCompany(Company company) {
        return new ResponseCompanyDto(
                company.getCompanyId(),
                company.getCompanyName(),
                company.getRegistrationNumber(),
                company.getRepresentName(),
                company.getRepresentCall(),
                company.getRepresentPhone(),
                company.getRepresentManagerName(),
                company.getRepresentManagerEmail(),
                company.getCreatedAt(),
                company.isContinueStatus(),
                company.isApproveStatus(),
                company.getCountry().getCountryId()
        );
    }
}
