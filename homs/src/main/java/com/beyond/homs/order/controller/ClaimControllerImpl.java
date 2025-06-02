package com.beyond.homs.order.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.order.data.ClaimSearchOption;
import com.beyond.homs.order.data.OrderSearchOption;
import com.beyond.homs.order.dto.ClaimListResponseDto;
import com.beyond.homs.order.dto.ClaimRequestDto;
import com.beyond.homs.order.dto.ClaimResponseDto;
import com.beyond.homs.order.dto.ClaimStatusUpdateDto;
import com.beyond.homs.order.dto.OrderResponseDto;
import com.beyond.homs.order.repository.OrderRepository;
import com.beyond.homs.order.service.ClaimService;
import com.beyond.homs.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/claim")
public class ClaimControllerImpl implements ClaimController {
    private final ClaimService claimService;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

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

    @GetMapping("/")
    @Override
    public ResponseEntity<ResponseDto<Page<ClaimListResponseDto>>> getAllOrders(
            @RequestParam(required = false) OrderSearchOption option,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {

        Page<ClaimListResponseDto> list = claimService.getAllClaimOrders(option,keyword,pageable);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "모든 주문을 성공적으로 조회했습니다.",
                list
        ));
    }

    @GetMapping("/{orderId}")
    @Override
    public ResponseEntity<ResponseDto<Page<ClaimResponseDto>>> getClaims(
            @PathVariable Long orderId,
            @RequestParam(required = false) ClaimSearchOption option,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<ClaimResponseDto> list = claimService.getClaims(orderId, option, keyword, pageable);
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
