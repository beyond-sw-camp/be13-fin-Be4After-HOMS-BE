package com.beyond.homs.order.service;

import com.beyond.homs.order.dto.OrderItemRequestDto;
import com.beyond.homs.order.dto.OrderItemResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderItemService {
    OrderItemResponseDto addOrderItem(Long orderId, @Valid OrderItemRequestDto requestDto);

    List<OrderItemResponseDto> getOrderItems(Long orderId);

    void deleteOrderItem(Long orderId, Long productId);

    OrderItemResponseDto updateOrderItem(Long orderId, Long productId, OrderItemRequestDto requestDto);
}
