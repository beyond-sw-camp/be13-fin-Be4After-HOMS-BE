package com.beyond.homs.order.service;

import com.beyond.homs.order.dto.OrderItemRequestDto;
import com.beyond.homs.order.dto.OrderItemResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface OrderItemService {

    List<OrderItemResponseDto> addOrderItem(Long orderId, List<OrderItemRequestDto> requestDtos);

    List<OrderItemResponseDto> getOrderItems(Long orderId);
    
    void deleteOrderItem(Long orderId, List<Long> productIds);

    OrderItemResponseDto updateOrderItem(Long orderId, Long productId, Long quantity);
}
