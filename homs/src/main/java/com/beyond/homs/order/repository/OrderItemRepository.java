package com.beyond.homs.order.repository;

import com.beyond.homs.order.entity.OrderItem;
import com.beyond.homs.order.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId>, OrderItemRepositoryCustom {
    List<OrderItem> findAllByOrder_OrderId(Long orderId);

    List<OrderItem> findAll();
}
