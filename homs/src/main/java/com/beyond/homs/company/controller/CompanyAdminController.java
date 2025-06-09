package com.beyond.homs.company.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.company.dto.CompanyDto;
import com.beyond.homs.company.dto.ResponseCompanyDto;
import com.beyond.homs.company.dto.UpdateContractStatusDto;
import com.beyond.homs.company.dto.UpdateTransactionStatusDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "거래처 API(관리자)", description = "거래처 API 목록")
public interface CompanyAdminController {
    @Operation(summary = "거래처 승인", description = "거래처를 승인합니다.")
    ResponseEntity<ResponseDto<Void>> grantCompany(Long companyId, UpdateContractStatusDto updateContractStatusDto);

    @Operation(summary = "거래처 목록", description = "거래처 목록을 조회 합니다.")
    ResponseEntity<ResponseDto<List<ResponseCompanyDto>>> getCompanyList();

    @Operation(summary = "거래처 조회", description = "거래처 정보를 상세 조회 합니다.")
    ResponseEntity<ResponseDto<ResponseCompanyDto>> getCompany(Long companyId);

    @Operation(summary = "거래 상태 변경", description = "거래처의 거래 상태를 변경합니다.")
    ResponseEntity<ResponseDto<Void>> updateTransactionStatus(Long companyId,UpdateTransactionStatusDto updateTransactionStatusDto);

    @Operation(summary = "거래처 정보 변경", description = "거래처의 정보를 변경합니다.")
    ResponseEntity<ResponseDto<Void>> updateCompany(Long companyId,CompanyDto updateCompanyDto);
}
