package com.beyond.homs.order.service;

import com.beyond.homs.order.dto.OrderItemRequestDto;
import com.beyond.homs.order.dto.OrderItemResponseDto;
import com.beyond.homs.order.dto.OrderItemSearchResponseDto;
import com.beyond.homs.product.data.ProductSearchOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderItemService {

    List<OrderItemResponseDto> addOrderItem(Long orderId, List<OrderItemRequestDto> requestDtos);

    Page<OrderItemSearchResponseDto> getOrderItems(Long orderId, ProductSearchOption option, String keyword, Pageable pageable);
    
    void deleteOrderItem(Long orderId, List<Long> productIds);

    OrderItemResponseDto updateOrderItem(Long orderId, Long productId, Long quantity);

    List<OrderItemResponseDto> getAllItems();
}
