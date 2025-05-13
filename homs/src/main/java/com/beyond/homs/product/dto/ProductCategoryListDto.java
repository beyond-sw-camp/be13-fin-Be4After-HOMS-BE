package com.beyond.homs.product.dto;

import com.beyond.homs.product.entity.ProductCategory;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProductCategoryListDto {

    private final Long categoryId;

    private final String categoryName;

    private final int sortNo;

    private final boolean active;

    private final List<ProductCategoryListDto> children;

    private ProductCategoryListDto(Long categoryId, String categoryName, int sortNo, boolean active, List<ProductCategoryListDto> children) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.sortNo = sortNo;
        this.active = active;
        this.children = children;
    }

    // 자식 카테고리까지 재귀 변환하기 위하여 of 사용
    public static ProductCategoryListDto of(ProductCategory productCategory) {
        return new ProductCategoryListDto(
                productCategory.getCategoryId(),
                productCategory.getCategoryName(),
                productCategory.getSortNo(),
                productCategory.isActive(),
                productCategory.getChildren().stream()
                        .map(ProductCategoryListDto::of)
                        .collect(Collectors.toList())
        );
    }
}
