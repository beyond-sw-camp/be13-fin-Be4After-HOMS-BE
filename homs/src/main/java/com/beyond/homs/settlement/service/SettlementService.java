package com.beyond.homs.settlement.service;

import com.beyond.homs.settlement.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SettlementService {
    List<SettlementResponseDto> getAllSettlement();

    List<SettlementResponseDto> getSettlementByUser(Long userId);

    Page<SettlementOrderInfoDto> getOrderInfo(Long orderId, Pageable pageable);

    SettlementCompanyInfoDto getCompanyInfoByOrderId(Long orderId);

    void updateSettlementStatus(SettlementUpdateRequestDto requestDto);

    SettlementResponseDto createSettlement(Long id, SettlementRequestDto requestDto);
}
