package com.beyond.homs.order.dto;

import com.beyond.homs.order.data.OrderStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class OrderResponseDto {
    private final Long orderId;

    private final String orderCode;

//    private final String companyName;

//    private final String deliveryName;

    private final LocalDateTime orderDate;

    private final LocalDateTime dueDate;

    private final boolean approved;

    private final Long parentOrderId;

    private final String rejectReason;

    private final String orderStatus;
}
