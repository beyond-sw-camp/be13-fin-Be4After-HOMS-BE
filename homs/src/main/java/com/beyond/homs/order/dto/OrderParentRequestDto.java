package com.beyond.homs.order.dto;

import com.beyond.homs.order.data.OrderStatusEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class OrderParentRequestDto {

    private final OrderItemRequestDto product;

    private final boolean approved;

    private final Long orderId;

    private final Long parentOrderId;
}
