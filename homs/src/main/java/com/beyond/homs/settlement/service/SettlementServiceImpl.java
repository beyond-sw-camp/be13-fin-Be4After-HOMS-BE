package com.beyond.homs.settlement.service;

import com.beyond.homs.company.entity.Company;
import com.beyond.homs.order.entity.Order;
import com.beyond.homs.order.entity.OrderItem;
import com.beyond.homs.order.repository.OrderItemRepository;
import com.beyond.homs.order.repository.OrderRepository;
import com.beyond.homs.settlement.data.SettleStatusEnum;
import com.beyond.homs.settlement.dto.*;
import com.beyond.homs.settlement.entity.Settlement;
import com.beyond.homs.settlement.repository.SettlementRepository;
import com.beyond.homs.wms.entity.DeliveryAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SettlementServiceImpl implements SettlementService {
    private final SettlementRepository settlementRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public List<SettlementResponseDto> getAllSettlement() {
        return settlementRepository.findAll().stream()
                .map(this::toSettlementResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SettlementResponseDto> getSettlementByUser(Long userId) {
        return settlementRepository.findAllByOrderUserUserId(userId).stream()
                .map(this::toSettlementResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SettlementCompanyInfoDto getCompanyInfoByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. ID=" + orderId));
        DeliveryAddress deliveryAddress = order.getDeliveryAddress();

        Company company = order.getUser().getCompany();

        return new SettlementCompanyInfoDto(
                company.getCompanyName(),
                company.getRegistrationNumber(),
                company.getRepresentName(),
                deliveryAddress.getDetailedAddress()
        );
    }

    @Override
    public List<SettlementOrderInfoDto> getOrderInfo(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을수 없습니다 ID=" + orderId));
        List<OrderItem> orderItems = orderItemRepository.findAllByOrder_OrderId(orderId);
        return orderItems.stream()
                .map(orderItem -> {
                    var product = orderItem.getProduct();
                    return SettlementOrderInfoDto.builder()
                            .productName(product.getProductName())
                            .quantity(orderItem.getQuantity())
                            .orderDate(order.getOrderDate())
                            .build();
                }).toList();
    }

    @Override
    @Transactional
    public void updateSettlementStatus(SettlementUpdateRequestDto requestDto) {
        Settlement settlement = settlementRepository.findById(requestDto.getSettlementId())
                .orElseThrow(() -> new IllegalArgumentException("정산 정보를 찾을 수 없습니다."));

        settlement.updateSettleStatus(SettleStatusEnum.SETTLED);
    }

    @Override
    @Transactional
    public SettlementResponseDto createSettlement(Long orderId, SettlementRequestDto requestDto){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. ID=" + orderId));

        Settlement settlement = Settlement.builder()
                .order(order)
                .settlementDate(requestDto.getSettlementDate())
                .taxInvoice(requestDto.getTexInvoice())
                .isSettled(requestDto.getIsSettled())
                .build();

        settlementRepository.save(settlement);
        return toSettlementResponse(settlement);
    }

    private SettlementResponseDto toSettlementResponse(Settlement settlement) {
        return new SettlementResponseDto(
                settlement.getId(),
                settlement.getOrder().getOrderId(),
                settlement.getOrder().getOrderCode(),
                settlement.getOrder().getUser().getCompany().getCompanyName(),
                settlement.getOrder().getDeliveryAddress().getDeliveryName(),
                settlement.getOrder().getOrderDate(),
                settlement.getSettlementDate(),
                settlement.getIsSettled().name(),
                settlement.getTaxInvoice()
        );
    }

}
