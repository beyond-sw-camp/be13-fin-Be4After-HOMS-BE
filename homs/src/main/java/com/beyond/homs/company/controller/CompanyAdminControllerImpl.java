package com.beyond.homs.company.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.company.dto.CompanyDto;
import com.beyond.homs.company.dto.ResponseCompanyDto;
import com.beyond.homs.company.dto.UpdateTransactionStatusDto;
import com.beyond.homs.company.service.CompanyAdminService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/company")
@RequiredArgsConstructor
public class CompanyAdminControllerImpl implements CompanyAdminController {
    private final CompanyAdminService companyAdminService;

    @PostMapping
    @Override
    public ResponseEntity<ResponseDto<Void>> enrollmentCompany(@RequestBody @Valid CompanyDto enrollmentCompanyDto) {
        companyAdminService.enrollmentCompany(enrollmentCompanyDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto<>(
                                HttpStatus.CREATED.value(),
                                "Company enrollment success",
                                null
                        ));
    }

    @PutMapping("/grant/{companyId}")
    @Override
    public ResponseEntity<ResponseDto<Void>> grantCompany(@PathVariable @NotBlank Long companyId) {
        companyAdminService.grantCompany(companyId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "Company enrollment success",
                        null
                ));
    }

    @GetMapping
    @Override
    public ResponseEntity<ResponseDto<List<ResponseCompanyDto>>> getCompanyList() {
        List<ResponseCompanyDto> companies = companyAdminService.getCompanyList();
        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "Company list retrieval success",
                        companies
                ));
    }

    @GetMapping("/{companyId}")
    @Override
    public ResponseEntity<ResponseDto<ResponseCompanyDto>> getCompany(@PathVariable @NotBlank Long companyId) {
        ResponseCompanyDto companyDto = companyAdminService.getCompany(companyId);
        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "Company retrieval success",
                        companyDto
                ));
    }

    @PutMapping("/status/{companyId}")
    @Override
    public ResponseEntity<ResponseDto<Void>> updateTransactionStatus(@PathVariable @NotBlank Long companyId,
                                                                     @RequestBody @Valid UpdateTransactionStatusDto updateTransactionStatusDto) {
        companyAdminService.updateTransactionStatus(companyId, updateTransactionStatusDto.isApprovedStatus());
        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "Company transaction status update success",
                        null
                ));
    }

    @PutMapping("/{companyId}")
    @Override
    public ResponseEntity<ResponseDto<Void>> updateCompany(@PathVariable @NotBlank Long companyId,
                                                           @RequestBody @Valid CompanyDto updateCompanyDto) {
        companyAdminService.updateCompany(companyId, updateCompanyDto);
        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "Company update success",
                        null
                ));
    }
}
