package com.beyond.homs.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class OrderItemSearchResponseDto {
    private final long orderId;

    private final String orderCode;

    private final String companyName;

    private final LocalDateTime orderDate;

    private final LocalDateTime dueDate;

    private final Long productId;

    private final String productName;

    private final String productDomain;

    private final String productCategory;

    private final Long productMinQuantity;

    private final Long productQuantity;
}
