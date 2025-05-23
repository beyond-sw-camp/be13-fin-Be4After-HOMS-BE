package com.beyond.homs.product.dto;

import com.beyond.homs.product.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    private String productName;

    private String productFeature;

    private String productUsage;

    private Long productMinQuantity;

    private Long categoryId;
}
