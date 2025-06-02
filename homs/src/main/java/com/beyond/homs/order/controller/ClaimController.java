package com.beyond.homs.order.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.order.data.ClaimSearchOption;
import com.beyond.homs.order.data.OrderSearchOption;
import com.beyond.homs.order.dto.ClaimListResponseDto;
import com.beyond.homs.order.dto.ClaimRequestDto;
import com.beyond.homs.order.dto.ClaimResponseDto;
import com.beyond.homs.order.dto.ClaimStatusUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "클레임 API", description = "클레임 API 목록")
public interface ClaimController {

    @Operation(summary = "클레임 생성", description = "클레임을 생성합니다.")
    ResponseEntity<ResponseDto<ClaimResponseDto>> createClaim(
            @Valid @RequestBody ClaimRequestDto claimRequestDto);

    @Operation(summary = "클레임이 있는 주문 조회", description = "클레임이 있는 모든 주문을 조회합니다.")
    ResponseEntity<ResponseDto<Page<ClaimListResponseDto>>> getAllOrders(
            @RequestParam(required = false) OrderSearchOption option,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, page = 0) Pageable pageable);

    @Operation(summary = "클레임 조회", description = "해당 주문의 모든 클레임 목록을 조회합니다.")
    ResponseEntity<ResponseDto<Page<ClaimResponseDto>>> getClaims(
            @PathVariable Long orderId,
            @RequestParam(required = false) Long claimId,
            @RequestParam(required = false) ClaimSearchOption option,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, page = 0) Pageable pageable);

    @Operation(summary = "클레임 상태 변경", description = "해당 클레임의 상태를 변경합니다.")
    ResponseEntity<ResponseDto<Void>> updateClaimStatus(
            @PathVariable Long claimId,
            @Valid @RequestBody ClaimStatusUpdateDto dto);
}
