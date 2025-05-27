package com.beyond.homs.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class OrderItemRequestDto {
    
    private final Long productId;

    private final Long quantity;
}
