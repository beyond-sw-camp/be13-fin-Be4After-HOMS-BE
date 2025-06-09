package com.beyond.homs.wms.dto;

import lombok.Getter;

@Getter
public class DeliveryAddRequestDto {
  private String deliveryName;

  private String postalCode;

  private String streetAddress;

  private String detailedAddress;

  private String reference;

  private Long companyId;
}
