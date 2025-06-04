package com.beyond.homs.order.service;

import com.beyond.homs.order.data.OrderSearchOption;
import com.beyond.homs.order.dto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface OrderService {
    OrderResponseDto createOrder();

    OrderResponseDto getOrder(Long orderId);

    Page<OrderResponseDto> getAllOrders(OrderSearchOption option, String keyword, Pageable pageable);

    OrderResponseDto updateOrder(Long orderId, @Valid OrderRequestDto requestDto);

    void deleteOrder(Long orderId);

    void setApprove(Long orderId, OrderApproveRequestDto requestDto);

    OrderResponseDto updateOrderDate(Long orderId, OrderDateRequestDto requestDto);

    OrderResponseDto getOrderByCode(String orderCode);

    // List<OrderResponseDto> getOrdersByUser(Long userId);

    List<OrderResponseDto> getChildOrders(Long parentOrderId);

    List<OrderDeliveryResponseDTO> getDeliveryInfo();

    List<OrderDeliveryResponseDTO> getDeliveryInfoByUser(Long userId);
}
