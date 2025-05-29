package com.beyond.homs.contract.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.contract.data.ContractSearchOption;
import com.beyond.homs.contract.dto.ContractListDto;
import com.beyond.homs.contract.dto.ContractRequestDto;
import com.beyond.homs.contract.dto.ContractResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "계약 API", description = "계약 API 목록")
public interface ContractController {

    @Operation(summary = "계약 생성", description = "계약을 생성합니다.")
    ResponseEntity<ResponseDto<ContractResponseDto>> createContract(
            @Valid @RequestBody ContractRequestDto contractRequestDto);

    @Operation(summary = "계약 전체 조회", description = "전체 계약을 조회합니다.")
    ResponseEntity<ResponseDto<Page<ContractListDto>>> contractList(
            @RequestParam(required = false) ContractSearchOption option,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, page = 0) Pageable pageable);

    @Operation(summary = "계약 상세 조회", description = "계약을 상세 조회합니다.")
    ResponseEntity<ResponseDto<ContractResponseDto>> contractDetail(
            @PathVariable Long contractId);
}
