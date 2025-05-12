package com.beyond.homs.company.service;

import com.beyond.homs.company.dto.RequestCompanyDto;
import com.beyond.homs.company.dto.ResponseCompanyDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyAdminServiceImpl implements CompanyAdminService {
    @Override
    public void enrollmentCompany(RequestCompanyDto enrollmentCompanyDto) {

    }

    @Override
    public void grantCompany(Long companyId) {

    }

    @Override
    public List<ResponseCompanyDto> getCompanyList() {
        return List.of();
    }

    @Override
    public ResponseCompanyDto getCompany(Long companyId) {
        return null;
    }

    @Override
    public void updateTransactionStatus(Long companyId, Boolean status) {

    }

    @Override
    public void updateCompany(Long companyId, RequestCompanyDto updateCompanyDto) {

    }
}
