package com.beyond.homs.settlement.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.order.dto.OrderResponseDto;
import com.beyond.homs.order.service.OrderService;
import com.beyond.homs.settlement.dto.SettlementCompanyInfoDto;
import com.beyond.homs.settlement.dto.SettlementOrderInfoDto;
import com.beyond.homs.settlement.dto.SettlementResponseDto;
import com.beyond.homs.settlement.dto.SettlementUpdateRequestDto;
import com.beyond.homs.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ResponseDto<List<SettlementOrderInfoDto>>> getOrderInfo(
            @PathVariable("orderId") Long orderId) {
        List<SettlementOrderInfoDto> list = settlementService.getOrderInfo(orderId);
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
}
