package com.beyond.homs.order.repository;

import com.beyond.homs.order.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    // 연관관계에 해당하는 company 까지 한번에 미리 불러옴
    @Query("SELECT o FROM Order o JOIN FETCH o.user u JOIN FETCH u.company c WHERE o.orderId = :orderId")
    Optional<Order> findByIdWithUserAndCompany(Long orderId);

    List<Order> findByOrderId(Long orderId);
}

