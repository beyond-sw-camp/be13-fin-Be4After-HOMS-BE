package com.beyond.homs.company.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.company.dto.CompanyDto;
import com.beyond.homs.company.dto.ResponseCompanyDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "거래처 API(사용자)", description = "거래처 API 목록")
public interface CompanyController {
    @Operation(summary = "거래처 등록", description = "거래처를 등록합니다.")
    ResponseEntity<ResponseDto<Void>> enrollmentCompany(CompanyDto enrollmentCompanyDto);

    @Operation(summary = "거래처 조회", description = "거래처 정보를 상세 조회 합니다.")
    ResponseEntity<ResponseDto<ResponseCompanyDto>> getCompany(Long companyId);
}
