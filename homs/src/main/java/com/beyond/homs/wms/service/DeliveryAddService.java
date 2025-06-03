package com.beyond.homs.wms.service;

import com.beyond.homs.wms.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DeliveryAddService {
  // 배송지 목록
  List<DeliveryAddResponseDto> getDeliveryList();

  // 배송지 추가
  @Transactional
  DeliveryAddResponseDto createDelivery(DeliveryAddRequestDto requestDto);

  // 배송지 수정
  @Transactional
  DeliveryAddResponseDto updateDelivery(Long addressId, DeliveryAddRequestDto requestDto);

  // 배송지 삭제
  @Transactional
  void deleteDelivery(Long addressId);
}
