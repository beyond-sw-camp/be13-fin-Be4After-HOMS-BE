package com.beyond.homs.company.service;

import com.beyond.homs.company.dto.CompanyDto;
import com.beyond.homs.company.dto.ResponseCompanyDto;

public interface CompanyService {
    void enrollmentCompany(CompanyDto enrollmentCompanyDto);

    ResponseCompanyDto getCompany(Long companyId);
}
