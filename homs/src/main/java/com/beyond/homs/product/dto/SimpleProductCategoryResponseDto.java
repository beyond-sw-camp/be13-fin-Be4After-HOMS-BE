package com.beyond.homs.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleProductCategoryResponseDto {
    private Long categoryId;

    private String categoryName;

    @Builder
    public SimpleProductCategoryResponseDto(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
