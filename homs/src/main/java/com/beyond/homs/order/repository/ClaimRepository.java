package com.beyond.homs.order.repository;

import com.beyond.homs.order.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long>, ClaimRepositoryCustom {
    // 해당 주문 관련 전체 클레임 조회
    List<Claim> findAllByOrderItem_OrderItemId_OrderId(Long orderId);

    List<Claim> findByOrderItem_Order_orderId(Long orderId);
}
