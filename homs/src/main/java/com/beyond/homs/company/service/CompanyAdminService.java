package com.beyond.homs.company.service;

import com.beyond.homs.company.dto.CompanyDto;
import com.beyond.homs.company.dto.ResponseCompanyDto;
import com.beyond.homs.company.dto.UpdateCompanyDto;

import java.util.List;

public interface CompanyAdminService {
    void grantCompany(Long companyId, Boolean status);

    List<ResponseCompanyDto> getCompanyList();

    ResponseCompanyDto getCompany(Long companyId);

    void updateTransactionStatus(Long companyId, Boolean status);

    void updateCompany(Long companyId, CompanyDto companyDto);

}
