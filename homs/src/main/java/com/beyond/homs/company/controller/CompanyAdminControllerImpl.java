package com.beyond.homs.company.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.company.dto.RequestCompanyDto;
import com.beyond.homs.company.dto.ResponseCompanyDto;
import com.beyond.homs.company.dto.UpdateTransactionStatusDto;
import com.beyond.homs.company.service.CompanyAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/company")
@RequiredArgsConstructor
public class CompanyAdminControllerImpl implements CompanyAdminController {
    private final CompanyAdminService companyAdminService;

    @Override
    public ResponseEntity<ResponseDto<Void>> enrollmentCompany(RequestCompanyDto enrollmentCompanyDto) {
        companyAdminService.enrollmentCompany(enrollmentCompanyDto);
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto<Void>> grantCompany(Long companyId) {
        companyAdminService.grantCompany(companyId);
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto<List<ResponseCompanyDto>>> getCompanyList() {
        companyAdminService.getCompanyList();
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto<ResponseCompanyDto>> getCompany(Long companyId) {
        companyAdminService.getCompany(companyId);
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto<Void>> updateTransactionStatus(Long companyId, UpdateTransactionStatusDto updateTransactionStatusDto) {
        companyAdminService.updateTransactionStatus(companyId, updateTransactionStatusDto.isApprovedStatus());
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto<Void>> updateCompany(Long companyId, RequestCompanyDto updateCompanyDto) {
        companyAdminService.updateCompany(companyId, updateCompanyDto);
        return null;
    }
}
