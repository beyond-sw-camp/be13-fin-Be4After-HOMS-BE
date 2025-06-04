package com.beyond.homs.settlement.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@RequiredArgsConstructor
public class SettlementResponseDto {
    private final Long settlementId;

    private final Long orderId;

    private final String orderCode;

    private final String companyName;

    private final String companyAddress;

    private final Date orderDate;

    private final LocalDateTime settlementDate;

    private final String isSettled;

    private final String taxInvoice;

}
