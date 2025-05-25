package com.beyond.homs.product.entity;

import com.beyond.homs.product.dto.ProductRequestDto;
import com.beyond.homs.wms.entity.Inventory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(columnDefinition = "TEXT", name = "product_feature")
    private String productFeature;

    @Column(columnDefinition = "TEXT", name = "product_usage")
    private String productUsage;

    @Column(name = "product_min_quantity")
    private Long productMinQuantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;
    
    // 재고와의 연관관계 설정
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Inventory> inventories = new ArrayList<>();

    @Builder
    public Product(String productName, String productFeature, String productUsage, Long productMinQuantity, ProductCategory category) {
        this.productName = productName;
        this.productFeature = productFeature;
        this.productUsage = productUsage;
        this.productMinQuantity = productMinQuantity;
        this.category = category;
    }

    public void update(ProductRequestDto requestDto, ProductCategory category) {
        this.productName = requestDto.getProductName();
        this.productFeature = requestDto.getProductFeature();
        this.productUsage = requestDto.getProductUsage();
        this.productMinQuantity = requestDto.getProductMinQuantity();
        this.category = category;
    }
}
