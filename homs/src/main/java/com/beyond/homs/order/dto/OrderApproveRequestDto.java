package com.beyond.homs.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderApproveRequestDto {
    private final boolean isApproved;

    private final String rejectReason;
}
