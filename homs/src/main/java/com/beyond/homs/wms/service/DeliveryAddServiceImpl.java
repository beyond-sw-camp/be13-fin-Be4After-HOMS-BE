package com.beyond.homs.wms.service;

import com.beyond.homs.common.exception.exceptions.CustomException;
import com.beyond.homs.common.exception.messages.ExceptionMessage;
import com.beyond.homs.company.entity.Company;
import com.beyond.homs.company.repository.CompanyRepository;
import com.beyond.homs.order.repository.OrderRepository;
import com.beyond.homs.wms.dto.DeliveryAddRequestDto;
import com.beyond.homs.wms.dto.DeliveryAddResponseDto;
import com.beyond.homs.wms.entity.DeliveryAddress;
import com.beyond.homs.wms.repository.DeliveryAddRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryAddServiceImpl implements DeliveryAddService{

  private final DeliveryAddRepository deliveryAddRepository;
  private final CompanyRepository companyRepository;
  private final OrderRepository orderRepository;

  // 배송지 목록
  @Override
  public List<DeliveryAddResponseDto> getDeliveryList() {
    List<DeliveryAddress> addresses = deliveryAddRepository.findAll();
    // List<Order> orders = orderRepository.findAll();

    return addresses.stream()
        .map(address -> DeliveryAddResponseDto.builder()
            .addressId(address.getAddressId())
            .deliveryName(address.getDeliveryName())
            .postalCode(address.getPostalCode())
            .streetAddress(address.getStreetAddress())
            .detailedAddress(address.getDetailedAddress())
            .reference(address.getReference())
            .companyId(address.getCompany().getCompanyId())
            .companyName(address.getCompany().getCompanyName())
            .build())
        .toList();
  }
  // 배송지 단일 상세
  @Override
  public DeliveryAddResponseDto getDeliveryDetail(Long addressId) {
    DeliveryAddress address = deliveryAddRepository.findByAddressId(addressId);

    return DeliveryAddResponseDto.builder()
                    .addressId(address.getAddressId())
                    .deliveryName(address.getDeliveryName())
                    .postalCode(address.getPostalCode())
                    .streetAddress(address.getStreetAddress())
                    .detailedAddress(address.getDetailedAddress())
                    .reference(address.getReference())
                    .companyId(address.getCompany().getCompanyId())
                    .companyName(address.getCompany().getCompanyName())
                    .build();
  }

  @Override
  public List<DeliveryAddResponseDto> getDeliveryListByCompanyId(Long companyId) {
    List<DeliveryAddress> addresses = deliveryAddRepository.findByCompany_CompanyId(companyId);

    return addresses.stream()
        .map(address -> DeliveryAddResponseDto.builder()
            .addressId(address.getAddressId())
            .deliveryName(address.getDeliveryName())
            .postalCode(address.getPostalCode())
            .streetAddress(address.getStreetAddress())
            .detailedAddress(address.getDetailedAddress())
            .reference(address.getReference())
            .companyId(address.getCompany().getCompanyId())
            .build())
        .toList();
  }

  //배송지 추가
  @Override
  public DeliveryAddResponseDto createDelivery(DeliveryAddRequestDto requestDto) {
    Company company = companyRepository.findById(requestDto.getCompanyId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회사입니다."));

    DeliveryAddress address = DeliveryAddress.builder()
        .deliveryName(requestDto.getDeliveryName())
        .postalCode(requestDto.getPostalCode())
        .streetAddress(requestDto.getStreetAddress())
        .detailedAddress(requestDto.getDetailedAddress())
        .reference(requestDto.getReference())
        .company(company)
        .build();

    DeliveryAddress saved = deliveryAddRepository.save(address);

    return DeliveryAddResponseDto.builder()
        .addressId(saved.getAddressId())
        .deliveryName(saved.getDeliveryName())
        .postalCode(saved.getPostalCode())
        .streetAddress(saved.getStreetAddress())
        .detailedAddress(saved.getDetailedAddress())
        .reference(saved.getReference())
        .companyId(saved.getCompany().getCompanyId())
        .build();
  }

  // 배송지 수정
  @Override
  public DeliveryAddResponseDto updateDelivery(Long addressId, DeliveryAddRequestDto requestDto) {
    DeliveryAddress address = deliveryAddRepository.findById(addressId)
        .orElseThrow(() -> new RuntimeException("해당 배송지가 존재하지 않습니다."));

    Company company = companyRepository.findById(requestDto.getCompanyId())
        .orElseThrow(() -> new RuntimeException("해당 회사가 존재하지 않습니다."));

    address.updateDeliveryAddress(requestDto, company);
    deliveryAddRepository.save(address);

    return DeliveryAddResponseDto.builder()
        .addressId(address.getAddressId())
        .deliveryName(address.getDeliveryName())
        .postalCode(address.getPostalCode())
        .streetAddress(address.getStreetAddress())
        .detailedAddress(address.getDetailedAddress())
        .reference(address.getReference())
        .companyId(address.getCompany().getCompanyId())
        .build();
  }

  // 배송지 삭제
  @Override
  public void deleteDelivery(Long addressId) {
    DeliveryAddress address = deliveryAddRepository.findById(addressId)
        .orElseThrow(() -> new CustomException(ExceptionMessage.WAREHOUSE_NOT_FOUND));

    deliveryAddRepository.deleteById(addressId);
  }
}
