package com.beyond.homs.order.dto;

import com.beyond.homs.order.data.OrderStatusEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class OrderParentRequestDto {

    private final List<OrderItemRequestDto> product;

    private final Long orderId;
}
