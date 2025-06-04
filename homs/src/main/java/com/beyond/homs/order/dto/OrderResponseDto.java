package com.beyond.homs.order.dto;

import com.beyond.homs.order.data.OrderStatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@RequiredArgsConstructor
@Builder
public class OrderResponseDto {
    private final Long orderId;

    private final String orderCode;

    private final String companyName;

    private final String deliveryName;

    private final Date orderDate;

    private final Date dueDate;

    private final boolean approved;

    private final Long parentOrderId;

    private final String rejectReason;

    private final OrderStatusEnum orderStatus;
}
