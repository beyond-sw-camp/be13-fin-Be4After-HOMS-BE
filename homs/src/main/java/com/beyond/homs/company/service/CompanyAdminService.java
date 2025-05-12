package com.beyond.homs.company.service;

import com.beyond.homs.company.dto.CompanyDto;
import com.beyond.homs.company.dto.ResponseCompanyDto;

import java.util.List;

public interface CompanyAdminService {
    void enrollmentCompany(CompanyDto enrollmentCompanyDto);

    void grantCompany(Long companyId);

    List<ResponseCompanyDto> getCompanyList();

    ResponseCompanyDto getCompany(Long companyId);

    void updateTransactionStatus(Long companyId, Boolean status);

    void updateCompany(Long companyId, CompanyDto updateCompanyDto);
}
