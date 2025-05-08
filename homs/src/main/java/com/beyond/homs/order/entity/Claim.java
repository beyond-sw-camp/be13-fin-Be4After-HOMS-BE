package com.beyond.homs.order.entity;

import com.beyond.homs.order.data.ClaimEnum;
import com.beyond.homs.order.data.ClaimStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "claim")
@Getter
@NoArgsConstructor
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id")
    private Long claimId;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "order_id", referencedColumnName = "order_id"),
            @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    })
    private OrderItem orderItem;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClaimEnum reason;

    @Column(length = 1024)
    private String details;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClaimStatusEnum status;

    @Builder
    public Claim(OrderItem orderItem, ClaimEnum reason, String details, ClaimStatusEnum status) {
        this.orderItem = orderItem;
        this.reason = reason;
        this.details = details;
        this.status = status;
    }
}
