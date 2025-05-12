package com.beyond.homs.company.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.company.dto.CompanyDto;
import com.beyond.homs.company.dto.ResponseCompanyDto;
import com.beyond.homs.company.dto.UpdateTransactionStatusDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CompanyAdminController {
    ResponseEntity<ResponseDto<Void>> enrollmentCompany(CompanyDto enrollmentCompanyDto);

    ResponseEntity<ResponseDto<Void>> grantCompany(Long companyId);

    ResponseEntity<ResponseDto<List<ResponseCompanyDto>>> getCompanyList();

    ResponseEntity<ResponseDto<ResponseCompanyDto>> getCompany(Long companyId);

    ResponseEntity<ResponseDto<Void>> updateTransactionStatus(Long companyId,
                                                              UpdateTransactionStatusDto updateTransactionStatusDto);

    ResponseEntity<ResponseDto<Void>> updateCompany(Long companyId,
                                                    CompanyDto updateCompanyDto);
}
