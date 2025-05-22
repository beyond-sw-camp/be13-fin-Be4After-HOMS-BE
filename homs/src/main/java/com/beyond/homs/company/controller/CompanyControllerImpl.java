package com.beyond.homs.company.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.company.dto.CompanyDto;
import com.beyond.homs.company.dto.ResponseCompanyDto;
import com.beyond.homs.company.service.CompanyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/company")
@Validated
@RequiredArgsConstructor
public class CompanyControllerImpl implements CompanyController {
    private final CompanyService companyService;

    @PostMapping
    @Override
    public ResponseEntity<ResponseDto<Void>> enrollmentCompany(@RequestBody @Valid CompanyDto enrollmentCompanyDto) {
        companyService.enrollmentCompany(enrollmentCompanyDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "Company enrollment success",
                        null
                ));
    }

    @GetMapping("/{companyId}")
    @Override
    public ResponseEntity<ResponseDto<ResponseCompanyDto>> getCompany(@PathVariable @NotBlank Long companyId) {
        ResponseCompanyDto companyDto = companyService.getCompany(companyId);
        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "Company retrieval success",
                        companyDto
                ));
    }
}
