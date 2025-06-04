package com.beyond.homs.wms.dto;

import com.beyond.homs.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DeliveryAddResponseDto {
  private Long addressId;

  private String deliveryName;

  private String postalCode;

  private String streetAddress;

  private String detailedAddress;

  private String reference;

  private Long companyId;

  private String companyName;   // 거래처명 추가

}