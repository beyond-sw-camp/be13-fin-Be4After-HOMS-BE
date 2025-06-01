package com.beyond.homs.settlement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class SettlementOrderInfoDto {
    private String productName;
    private Long quantity;
    private final Date orderDate;
}
