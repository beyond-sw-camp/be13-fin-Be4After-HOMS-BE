package com.beyond.homs.contract.dto;

import com.beyond.homs.product.entity.ProductCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ContractResponseDto {
    private final Long contractId;

    private final String companyName;

    private final String productName;

    private final String representManagerName;

    private final LocalDateTime contractStartAt;

    private final LocalDateTime contractStopAt;

    private final String categoryName;
}
