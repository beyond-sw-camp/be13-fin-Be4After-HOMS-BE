package com.beyond.homs.settlement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class SettlementOrderInfoDto {
    private String productName;
    private Long quantity;
    private final LocalDateTime orderDate;
}
