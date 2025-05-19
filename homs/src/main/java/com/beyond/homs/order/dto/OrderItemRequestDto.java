package com.beyond.homs.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderItemRequestDto {
    private final Long orderId;

    private final Long productId;

    private final Long quantity;
}
