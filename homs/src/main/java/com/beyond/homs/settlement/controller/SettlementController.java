package com.beyond.homs.settlement.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.settlement.dto.SettlementResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name="정산 API", description = "정산 API 목록")
public interface SettlementController {

    @Operation(summary = "정산 전체 조회", description = "모든 정상 정보를 조회합니다 (관리자용)")
    ResponseEntity<ResponseDto<List<SettlementResponseDto>>> getAllSettlement ();

    @Operation(summary = "사용자별 정산 조회", description = "사용자별 정산 조회합니다.")
    ResponseEntity<ResponseDto<List<SettlementResponseDto>>> getSettlementByUser(
            @PathVariable Long userId
    );

//    @Operation(summary = "사용자별 거래처 정보 조회", description = "세금명세서 작성을 위해 거래처 정보 조회합니다")
//    ResponseEntity<ResponseDto<List<SettlementResponseDto>>> getCompanyInfoByUser(
//            @PathVariable Long orderId
//    );

}


