package com.beyond.homs.settlement.service;

import com.beyond.homs.settlement.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SettlementService {
    List<SettlementResponseDto> getAllSettlement();

    List<SettlementResponseDto> getSettlementByUser(Long userId);

    List<SettlementOrderInfoDto> getOrderInfo(Long orderId);

    SettlementCompanyInfoDto getCompanyInfoByOrderId(Long orderId);

    void updateSettlementStatus(SettlementUpdateRequestDto requestDto);

    SettlementResponseDto createSettlement(Long id, SettlementRequestDto requestDto);
}
