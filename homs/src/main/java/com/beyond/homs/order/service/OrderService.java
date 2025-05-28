package com.beyond.homs.order.service;

import com.beyond.homs.order.dto.OrderApproveRequestDto;
import com.beyond.homs.order.dto.OrderRequestDto;
import com.beyond.homs.order.dto.OrderResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderResponseDto createOrder();

    OrderResponseDto getOrder(Long orderId);

    List<OrderResponseDto> getAllOrders();

    OrderResponseDto updateOrder(Long orderId, @Valid OrderRequestDto requestDto);

    void deleteOrder(Long orderId);

    void setApprove(Long orderId, OrderApproveRequestDto requestDto);

    OrderResponseDto getOrderByCode(String orderCode);

    // List<OrderResponseDto> getOrdersByUser(Long userId);

    List<OrderResponseDto> getChildOrders(Long parentOrderId);
}
