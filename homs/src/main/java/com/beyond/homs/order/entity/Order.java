package com.beyond.homs.order.entity;

import com.beyond.homs.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
/*
* table 명을 order로 했을 때, 테이블 생성 과정에서 에러가 발생해서 order_table로 변경
* */
@Entity
@Table(name = "order_table")
@Getter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_code", nullable = false, length = 1024)
    private String orderCode;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "order_date", nullable = false)
    @CreatedDate
    private LocalDateTime orderDate;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id2", referencedColumnName = "order_id")
    private Order orderId2;

    @Column(name = "reject_reason", length = 1024)
    private String rejectReason;

    @Builder
    public Order(String orderCode, LocalDateTime dueDate, boolean isApproved,
                 User user, Order orderId2, String rejectReason) {
        this.orderCode = orderCode;
        this.dueDate = dueDate;
        this.isApproved = isApproved;
        this.user = user;
        this.orderId2 = orderId2;
        this.rejectReason = rejectReason;
    }
}
