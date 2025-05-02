package com.beyond.homs.contract.entity;

import com.beyond.homs.company.entity.Company;
import com.beyond.homs.product.entity.Product;
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

import java.time.LocalDateTime;

@Entity
@Table(name = "contract")
@Getter
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long contractId;

    @Column(name = "contract_start_at", nullable = false)
    private LocalDateTime contractStartAt;

    @Column(name = "contract_stop_at", nullable = false)
    private LocalDateTime contractStopAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Builder
    public Contract(LocalDateTime contractStartAt, LocalDateTime contractStopAt, Company company, Product product) {
        this.contractStartAt = contractStartAt;
        this.contractStopAt = contractStopAt;
        this.company = company;
        this.product = product;
    }
}
