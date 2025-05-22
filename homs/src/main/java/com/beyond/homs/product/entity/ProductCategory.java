package com.beyond.homs.product.entity;

import com.beyond.homs.product.dto.ProductCategoryRequestDto;
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
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"parent","children"})
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "sort_no")
    private int sortNo;

    @Column(name = "is_active")
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "category_id_parent", nullable = true)
    private ProductCategory parent;

    @OneToMany(mappedBy = "parent",
               fetch = FetchType.LAZY,
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    @OrderBy("sortNo ASC")
    private List<ProductCategory> children = new ArrayList<>();

    @Builder
    public ProductCategory(String categoryName, int sortNo, boolean active, ProductCategory parent) {
        this.categoryName = categoryName;
        this.sortNo = sortNo;
        this.active = active;
        this.parent = parent;
    }

    public void update(ProductCategoryRequestDto requestDto) {
        this.categoryName = requestDto.getCategoryName();
        this.sortNo = requestDto.getSortNo();
        this.active = requestDto.isActive();
    }
}
