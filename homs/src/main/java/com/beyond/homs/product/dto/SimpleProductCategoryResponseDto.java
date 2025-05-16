package com.beyond.homs.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleProductCategoryResponseDto {
    private Long categoryId;

    private String categoryName;

    private String upperCategoryName;

    private String processName;

    @Builder
    public SimpleProductCategoryResponseDto(Long categoryId, String categoryName, String upperCategoryName, String processName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.upperCategoryName = upperCategoryName;
        this.processName = processName;
    }
}
