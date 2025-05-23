package com.beyond.homs.order.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.order.dto.ClaimRequestDto;
import com.beyond.homs.order.dto.ClaimResponseDto;
import com.beyond.homs.order.dto.ClaimStatusUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "클레임 API", description = "클레임 API 목록")
public interface ClaimController {

    @Operation(summary = "클레임 생성", description = "클레임을 생성합니다.")
    ResponseEntity<ResponseDto<ClaimResponseDto>> createClaim(
            @Valid @RequestBody ClaimRequestDto claimRequestDto);

    @Operation(summary = "클레임 조회", description = "해당 주문의 모든 클레임 목록을 조회합니다.")
    ResponseEntity<ResponseDto<List<ClaimResponseDto>>> getClaims(
            @PathVariable("orderId") Long OrderId);

    @Operation(summary = "클레임 상태 변경", description = "해당 클레임의 상태를 변경합니다.")
    ResponseEntity<ResponseDto<Void>> updateClaimStatus(
            @PathVariable Long claimId,
            @Valid @RequestBody ClaimStatusUpdateDto dto);

}
