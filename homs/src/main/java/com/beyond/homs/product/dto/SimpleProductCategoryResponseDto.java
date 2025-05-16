package com.beyond.homs.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleProductCategoryResponseDto {
    private Long categoryId;

    private String productDomain;

    private String productCategory;

    private String manufacturingProcess;

    @Builder
    public SimpleProductCategoryResponseDto(Long categoryId, String productDomain, String productCategory, String manufacturingProcess) {
        this.categoryId = categoryId;
        this.productDomain = productDomain;
        this.productCategory = productCategory;
        this.manufacturingProcess = manufacturingProcess;
    }
}
