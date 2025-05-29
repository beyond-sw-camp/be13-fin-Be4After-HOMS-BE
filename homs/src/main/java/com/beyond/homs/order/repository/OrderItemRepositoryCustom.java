package com.beyond.homs.order.repository;

import com.beyond.homs.order.dto.OrderItemSearchResponseDto;
import com.beyond.homs.product.data.ProductSearchOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderItemRepositoryCustom {
    Page<OrderItemSearchResponseDto> findOrderItems(Long targetOrderId, ProductSearchOption option, String keyword, Pageable pageable);
}
