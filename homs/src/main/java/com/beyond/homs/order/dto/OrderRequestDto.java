package com.beyond.homs.order.dto;

import com.beyond.homs.order.data.OrderStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class OrderRequestDto {
    private final String orderCode;

    private final LocalDateTime dueDate;

    private final boolean approved;

    private final OrderStatusEnum orderStatus;

    private final Long userId;

    private final Long parentOrderId;

    private final String rejectReason;

//    private final Long deliveryAddressId;
}
