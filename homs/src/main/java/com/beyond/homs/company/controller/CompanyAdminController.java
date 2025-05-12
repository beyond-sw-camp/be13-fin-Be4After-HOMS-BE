package com.beyond.homs.company.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.company.dto.RequestCompanyDto;
import com.beyond.homs.company.dto.ResponseCompanyDto;
import com.beyond.homs.company.dto.UpdateTransactionStatusDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CompanyAdminController {
    ResponseEntity<ResponseDto<Void>> enrollmentCompany(@RequestBody @Valid RequestCompanyDto enrollmentCompanyDto);

    ResponseEntity<ResponseDto<Void>> grantCompany(@PathVariable @NotBlank Long companyId);

    ResponseEntity<ResponseDto<List<ResponseCompanyDto>>> getCompanyList();

    ResponseEntity<ResponseDto<ResponseCompanyDto>> getCompany(@PathVariable @NotBlank Long companyId);

    ResponseEntity<ResponseDto<Void>> updateTransactionStatus(@PathVariable @NotBlank Long companyId,
                                                              @RequestBody @Valid UpdateTransactionStatusDto updateTransactionStatusDto);

    ResponseEntity<ResponseDto<Void>> updateCompany(@PathVariable @NotBlank Long companyId,
                                                    @RequestBody @Valid RequestCompanyDto updateCompanyDto);
}
