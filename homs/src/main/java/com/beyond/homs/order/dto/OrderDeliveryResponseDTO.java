package com.beyond.homs.order.dto;

import com.beyond.homs.wms.entity.DeliveryAddress;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@RequiredArgsConstructor
@Builder
public class OrderDeliveryResponseDTO {
    private final Long orderId;          // 주문 ID

    private final String orderCode;      // 발주번호

    private final String companyName;    // 거래처명

    private final String deliveryName;   // 배송지명

    private final Date orderDate;     // 주문일

    private final Date deliveryDate;  // 출고예정일

    private final String waybill;              // 운송장 번호

    private final String deliveryStatus;       // 배송 상태
}
