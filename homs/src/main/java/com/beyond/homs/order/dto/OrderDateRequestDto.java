package com.beyond.homs.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@RequiredArgsConstructor
public class OrderDateRequestDto {

    private final Date dueDate;

//    private final Long deliveryAddressId;
}
