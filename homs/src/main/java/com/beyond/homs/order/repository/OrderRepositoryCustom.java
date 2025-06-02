package com.beyond.homs.order.repository;

import com.beyond.homs.order.data.OrderSearchOption;
import com.beyond.homs.order.dto.OrderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {
    Page<OrderResponseDto> findOrders(OrderSearchOption option, String keyword, Long userId, Pageable pageable);

    // 클레임이 있는 모든 주문 검색
    Page<OrderResponseDto> findClaimOrders(OrderSearchOption option, String keyword, Long userId, Pageable pageable);
}
