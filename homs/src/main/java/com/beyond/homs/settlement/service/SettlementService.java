package com.beyond.homs.settlement.service;

import com.beyond.homs.settlement.dto.SettlementRequestDto;
import com.beyond.homs.settlement.dto.SettlementResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SettlementService {
    List<SettlementResponseDto> getAllSettlement();
    List<SettlementResponseDto> getSettlementByUser(Long userId);
//    SettlementResponseDto createSettlement(@Valid SettlementRequestDto settlementRequestDto);
}
