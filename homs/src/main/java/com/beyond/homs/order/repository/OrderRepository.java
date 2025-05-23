package com.beyond.homs.order.repository;

import com.beyond.homs.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // 단건 조회
    Optional<Order> findByOrderCode(String orderCode);
    // 전체 조회
    List<Order> findAllByUser_UserId(Long userId);
    // 하위 주문 조회
    List<Order> findAllByParentOrder_OrderId(Long parentOrderId);
}

