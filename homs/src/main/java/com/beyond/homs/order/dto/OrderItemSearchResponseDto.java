package com.beyond.homs.order.dto;

import com.beyond.homs.order.data.OrderStatusEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class OrderItemSearchResponseDto {
    private final Long orderId;

    private final String orderCode;

    private final Long companyId;

    private final String companyName;

    private final Date orderDate;

    private final Date dueDate;

    private final OrderStatusEnum orderStatus;

    private final boolean approved;

    private final String rejectReason;

    private final String deliveryName;

    private final Long productId;

    private final String productName;

    private final String productDomain;

    private final String productCategory;

    private final Long productMinQuantity;

    private final Long productQuantity;
}
