package com.beyond.homs.settlement.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.order.service.OrderService;
import com.beyond.homs.settlement.dto.*;
import com.beyond.homs.settlement.service.SettlementService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/settlement")
public class SettlementControllerImpl implements SettlementController {
    private final OrderService orderService;
    private final SettlementService settlementService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    @Override
    public ResponseEntity<ResponseDto<List<SettlementResponseDto>>> getAllSettlement() {
        List<SettlementResponseDto> list = settlementService.getAllSettlement();
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "모든 정산을 성공적으로 조회했습니다.",
                list)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("user/{userId}")
    @Override
    public ResponseEntity<ResponseDto<List<SettlementResponseDto>>> getSettlementByUser(
            @PathVariable("userId") Long userId) {
        List<SettlementResponseDto> list = settlementService.getSettlementByUser(userId);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "사용자별 정산 조회 성공",
                list));
    }

    @GetMapping("/{orderId}/companyInfo")
    @Override
    public ResponseEntity<ResponseDto<SettlementCompanyInfoDto>> getCompanyInfoByOrder(
            @PathVariable("orderId") Long orderId) {
        SettlementCompanyInfoDto dto = settlementService.getCompanyInfoByOrderId(orderId);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "주문별 거래처 조회",
                dto));
    }

    @GetMapping("/{orderId}/orderInfo")
    @Override
    public ResponseEntity<ResponseDto<Page<SettlementOrderInfoDto>>> getOrderInfo(
            @PathVariable("orderId") Long orderId,
            @PageableDefault(size = 3, page = 0) Pageable pageable) {
        Page<SettlementOrderInfoDto> list = settlementService.getOrderInfo(orderId, pageable);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "주문별 주문 상품 조회",
                list));
    }

    @PutMapping("/update")
    @Override
    public ResponseEntity<ResponseDto<String>> updateSettlementStatus(@RequestBody SettlementUpdateRequestDto requestDto) {
        settlementService.updateSettlementStatus(requestDto);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "정산 상태가 SETTLED로 업데이트되었습니다.",
                "SUCCESS"
        ));
    }

    @PostMapping("/{orderId}")
    @Override
    public ResponseEntity<ResponseDto<SettlementResponseDto>> createSettlement(
            @PathVariable Long orderId,
            @RequestBody SettlementRequestDto requestDto){
        SettlementResponseDto dto = settlementService.createSettlement(orderId,requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "새로운 정산이 생성되었습니다.",
                        dto
                ));
    }
}
