package com.beyond.homs.settlement.dto;

import com.beyond.homs.settlement.data.SettleStatusEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class SettlementRequestDto {
    private final LocalDateTime settlementDate;

    private final String texInvoice;

    private final SettleStatusEnum isSettled;
}
