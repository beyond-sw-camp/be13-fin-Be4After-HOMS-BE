package com.beyond.homs.order.entity;

import com.beyond.homs.product.entity.Product;
import com.beyond.homs.product.entity.ProductCategory;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@Getter
@NoArgsConstructor
public class OrderItem {
    @EmbeddedId
    private OrderItemId orderItemId;

    @Builder
    OrderItem(OrderItemId orderItemId) {
        this.orderItemId = orderItemId;
    }
}
