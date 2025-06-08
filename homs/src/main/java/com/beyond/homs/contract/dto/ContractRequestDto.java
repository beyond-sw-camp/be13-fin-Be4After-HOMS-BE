package com.beyond.homs.contract.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ContractRequestDto {
    private final Long companyId;

    private final Long productId;

    private final LocalDateTime contractStartAt;

    private final LocalDateTime contractStopAt;
}
