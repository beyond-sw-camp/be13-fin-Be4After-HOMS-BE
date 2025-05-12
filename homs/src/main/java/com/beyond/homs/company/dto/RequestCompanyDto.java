package com.beyond.homs.company.dto;

import com.beyond.homs.company.data.CountryEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * {
 * "nation":"국가",
 * "companyName": "회사명",
 * "registrationNumber":"사업자등록번호",
 * "representName":"대표자명",
 * "representCall":"대표연락처",
 * "representPhone":"담당",
 * "representManagerName": "담당자명",
 * "representManagerEmail": "담당지 이메일"
 * }
 */
public record RequestCompanyDto(
        @NotBlank CountryEnum nation,
        @NotBlank String companyName,
        @NotBlank String registrationNumber,
        @NotBlank String representName,
        @NotBlank String representCall,
        @NotBlank String representPhone,
        @NotBlank String representManagerName,
        @NotBlank @Email String representManagerEmail
) {}