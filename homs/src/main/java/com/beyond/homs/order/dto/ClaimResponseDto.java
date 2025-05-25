package com.beyond.homs.order.dto;

import com.beyond.homs.order.data.ClaimEnum;
import com.beyond.homs.order.data.ClaimStatusEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ClaimResponseDto {
    private final Long claimId;

    private final String orderCode;

    private final String productName;

    private final String companyName;

    private final ClaimEnum reason;

    private final String details;

    private final Long quantity;

    private final ClaimStatusEnum status;
}
