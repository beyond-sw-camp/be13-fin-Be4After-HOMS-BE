package com.beyond.homs.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class OrderDateRequestDto {

    private final LocalDateTime dueDate;

//    private final Long deliveryAddressId;
}
