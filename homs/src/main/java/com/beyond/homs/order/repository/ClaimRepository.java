package com.beyond.homs.order.repository;

import com.beyond.homs.order.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    // 필요하다면, 특정 주문상품에 대한 클레임 조회용 메서드도 정의할 수 있습니다.
    // List<Claim> findAllByOrderItem_OrderItemId(OrderItemId orderItemId);
}
