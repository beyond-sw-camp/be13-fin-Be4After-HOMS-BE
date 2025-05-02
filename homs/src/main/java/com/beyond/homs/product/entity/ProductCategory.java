package com.beyond.homs.product.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "sort_no")
    private int sno;

    @Column(name = "is_active")
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // false 설정 시 부모가 반드시 있어야 함.
    @JoinColumn(name = "category_id_parent")
    private ProductCategory parent;

    @OneToMany(mappedBy = "parent",
               fetch = FetchType.LAZY,
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    @OrderBy("sno ASC")
    private List<ProductCategory> children = new ArrayList<>();

    @Builder
    public ProductCategory(String categoryName, int sno, boolean active, ProductCategory parent) {
        this.categoryName = categoryName;
        this.sno = sno;
        this.active = active;
        this.parent = parent;
    }
}
