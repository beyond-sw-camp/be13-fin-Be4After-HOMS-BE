package com.beyond.homs.settlement.service;

import com.beyond.homs.settlement.dto.SettlementCompanyInfoDto;
import com.beyond.homs.settlement.dto.SettlementOrderInfoDto;
import com.beyond.homs.settlement.dto.SettlementResponseDto;
import com.beyond.homs.settlement.dto.SettlementUpdateRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SettlementService {
    List<SettlementResponseDto> getAllSettlement();

    List<SettlementResponseDto> getSettlementByUser(Long userId);

    List<SettlementOrderInfoDto> getOrderInfo(Long orderId);

    SettlementCompanyInfoDto getCompanyInfoByOrderId(Long orderId);

    void updateSettlementStatus(SettlementUpdateRequestDto requestDto);
}
