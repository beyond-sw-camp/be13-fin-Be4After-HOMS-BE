package com.beyond.homs.settlement.entity;

import com.beyond.homs.order.entity.Order;
import com.beyond.homs.settlement.data.SettleStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "settlement")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Settlement {
    @Id
    @JoinColumn(name = "order_id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "settlement_date")
    @LastModifiedDate
    private LocalDateTime settlementDate;

    @Column(name = "tax_invoice")
    private String taxInvoice;

    @Column(name = "is_settled",nullable = false)
    @Enumerated(EnumType.STRING)
    private SettleStatusEnum isSettled;

    @Builder
    public Settlement(String taxInvoice, SettleStatusEnum isSettled) {
        this.taxInvoice = taxInvoice;
        this.isSettled = isSettled;
    }
}
