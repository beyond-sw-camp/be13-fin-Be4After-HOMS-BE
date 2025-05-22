package com.beyond.homs.order.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.order.dto.ClaimRequestDto;
import com.beyond.homs.order.dto.ClaimResponseDto;
import com.beyond.homs.order.dto.ClaimStatusUpdateDto;
import com.beyond.homs.order.service.ClaimService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/claim")
public class ClaimControllerImpl implements ClaimController {
    private final ClaimService claimService;

    @PostMapping("/")
    @Override
    public ResponseEntity<ResponseDto<ClaimResponseDto>> createClaim(
            @Valid @RequestBody ClaimRequestDto requestDto) {
        ClaimResponseDto dto = claimService.createClaim(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "클레임이 성공적으로 신청되었습니다.",
                        dto
                ));
    }

    @GetMapping("/{orderId}")
    @Override
    public ResponseEntity<ResponseDto<List<ClaimResponseDto>>> getClaims(
            @PathVariable Long orderId) {
        List<ClaimResponseDto> list = claimService.getClaims(orderId);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "주문관련 모든 클레임을 성공적으로 조회했습니다.",
                list
        ));
    }

    @Override
    @PatchMapping("/{claimId}/status")
    public ResponseEntity<ResponseDto<Void>> updateClaimStatus(
            @PathVariable Long claimId,
            @Valid @RequestBody ClaimStatusUpdateDto dto) {
        claimService.updateStatus(claimId, dto.getStatus());
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "클레임 상태가 성공적으로 변경되었습니다.",
                null
        ));
    }
}
