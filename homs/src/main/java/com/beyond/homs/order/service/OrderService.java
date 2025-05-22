package com.beyond.homs.order.service;

import com.beyond.homs.order.dto.OrderRequestDto;
import com.beyond.homs.order.dto.OrderResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderResponseDto createOrder(@Valid OrderRequestDto requestDto);

    OrderResponseDto getOrder(Long orderId);

    List<OrderResponseDto> getAllOrders();

    OrderResponseDto updateOrder(Long orderId, @Valid OrderRequestDto requestDto);

    void deleteOrder(Long orderId);

    OrderResponseDto getOrderByCode(String orderCode);

    List<OrderResponseDto> getOrdersByUser(Long userId);

    List<OrderResponseDto> getChildOrders(Long parentOrderId);
}
