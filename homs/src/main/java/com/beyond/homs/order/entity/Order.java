package com.beyond.homs.order.entity;

import com.beyond.homs.company.data.CountryEnum;
import com.beyond.homs.order.data.OrderStatusEnum;
import com.beyond.homs.user.entity.User;
import com.beyond.homs.wms.entity.DeliveryAddress;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
/*
* table 명을 order로 했을 때, 테이블 생성 과정에서 에러가 발생해서 order_table로 변경
* */
@Entity
@Table(name = "order_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_code", nullable = false, length = 1024, unique = true)
    private String orderCode;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "order_date", nullable = false)
    @CreatedDate
    private LocalDateTime orderDate;

    @Column(name = "is_approved", nullable = false)
    private boolean approved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id_parent", referencedColumnName = "order_id")
    private Order parentOrder;

    @Column(name = "reject_reason", length = 1024)
    private String rejectReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private DeliveryAddress deliveryAddress;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;

    @Builder
    public Order(String orderCode, LocalDateTime dueDate, boolean approved,
                 User user, Order parentOrder, String rejectReason, DeliveryAddress deliveryAddress, OrderStatusEnum orderStatus) {
        this.orderCode = orderCode;
        this.dueDate = dueDate;
        this.approved = approved;
        this.user = user;
        this.parentOrder = parentOrder;
        this.rejectReason = rejectReason;
        this.deliveryAddress = deliveryAddress;
        this.orderStatus = orderStatus;
    }

//    public Order linkParent(Order parent) {
//        this.parentOrder = parent;
//        return this;
//    }

    public void updateOrderCode(String code) {
        this.orderCode = code;
    }

    // 새 납기일은 주문일(orderDate) 이후여야 한다.
    // 이미 완료된 주문은 납기일을 바꿀 수 없다.
    public void updateDueDate(LocalDateTime newDueDate) {
        if (newDueDate == null) {
            throw new IllegalArgumentException("납기일을 반드시 입력해야 합니다.");
        }
        if (newDueDate.isBefore(this.orderDate)) {
            throw new IllegalArgumentException("납기일은 주문일("
                    + this.orderDate + ") 이후여야 합니다.");
        }
        if (this.approved) {
            throw new IllegalStateException("승인된 주문은 납기일을 변경할 수 없습니다.");
        }
        this.dueDate = newDueDate;
    }

    // 이미 승인된 주문은 재호출 불가
    // rejectReason 을 클리어
    public void approve() {
        if (this.approved) {
            throw new IllegalStateException("이미 승인된 주문입니다. id=" + orderId);
        }
        this.approved = true;
        this.rejectReason = null;
    }

    // 승인된 주문은 거절할 수 없다.
    // rejectReason 이 null 이여도 허용
    public void reject(String reason) {
        if (this.approved) {
            throw new IllegalStateException("승인된 주문은 거절할 수 없습니다. id=" + orderId);
        }
        this.approved = false;
        // null 이거나 공백이면 그냥 null 로 설정
        this.rejectReason = (reason == null || reason.isBlank())
                ? null
                : reason.trim();
    }

    public void updateParentOrder(Order parent) {
        // 1) 자기 참조 금지
        if (parent != null && this.orderId != null
                && this.orderId.equals(parent.getOrderId())) {
            throw new IllegalArgumentException("자기 자신을 부모로 설정할 수 없습니다.");
        }
        // 2) 순환 참조 금지
        Order cursor = parent;
        while (cursor != null) {
            if (this.orderId != null && this.orderId.equals(cursor.getOrderId())) {
                throw new IllegalArgumentException("부모 주문과 순환 참조가 발생합니다.");
            }
            cursor = cursor.getParentOrder();
        }
        // 3) 비즈니스 상태 제약
//        if (this.approved) {
//            throw new IllegalStateException("승인된 주문은 부모 주문을 변경할 수 없습니다.");
//        }
        this.parentOrder = parent;
    }


//    // 주문 생성 후 24시간 이내에만 변경 가능하다.
//    public void updateDeliveryAddress(DeliveryAddress newAddress) {
//        if (newAddress == null) {
//            throw new IllegalArgumentException("새 배송지를 입력하세요.");
//        }
//        LocalDateTime cutoff = this.orderDate.plusHours(24);
//        if (LocalDateTime.now().isAfter(cutoff)) {
//            throw new IllegalStateException("주문 후 24시간이 지나면 배송지를 변경할 수 없습니다.");
//        }
//        this.deliveryAddress = newAddress;
//    }

    public void updateOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
    }
}
