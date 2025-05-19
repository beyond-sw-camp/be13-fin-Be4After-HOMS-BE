package com.beyond.homs.settlement.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.order.dto.OrderResponseDto;
import com.beyond.homs.order.service.OrderService;
import com.beyond.homs.settlement.dto.SettlementResponseDto;
import com.beyond.homs.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/settlement")
public class SettlementControllerImpl implements SettlementController {
    private final OrderService orderService;
    private final SettlementService settlementService;

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
}
