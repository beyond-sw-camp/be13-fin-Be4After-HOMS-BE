package com.beyond.homs.settlement.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.settlement.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name="정산 API", description = "정산 API 목록")
public interface SettlementController {

    @Operation(summary = "정산 전체 조회", description = "모든 정상 정보를 조회합니다 (관리자용)")
    ResponseEntity<ResponseDto<List<SettlementResponseDto>>> getAllSettlement ();

    @Operation(summary = "사용자별 정산 조회", description = "사용자별 정산 조회합니다.")
    ResponseEntity<ResponseDto<List<SettlementResponseDto>>> getSettlementByUser(
            @PathVariable Long userId
    );

    @Operation(summary = "세금계산서 거래처 정보 조회", description = "세금계산서 정산에 필요한 거래처 정보를 조회합니다.")
    ResponseEntity<ResponseDto<SettlementCompanyInfoDto>> getCompanyInfoByOrder(
            @PathVariable Long orderId
    );

    @Operation(summary = "세금계산서 주문 정보 조회", description = "세금계산서 정산에 필요하 주문 정보를 조회합니다")
    ResponseEntity<ResponseDto<Page<SettlementOrderInfoDto>>> getOrderInfo(
            @PathVariable("orderId") Long orderId,
            @PageableDefault(size = 3, page = 0) Pageable pageable);

    @Operation(summary = "세금명세서 상태 변경", description = "정산 관리 상태 변경합니다")
    ResponseEntity<ResponseDto<String>> updateSettlementStatus(
            @RequestBody SettlementUpdateRequestDto requestDto
    );

    @Operation(summary = "정산 생성", description = "정산을 생성합니다")
    ResponseEntity<ResponseDto<SettlementResponseDto>> createSettlement(
            @PathVariable("orderId") Long orderId,
            @RequestBody SettlementRequestDto requestDto
    );
}


