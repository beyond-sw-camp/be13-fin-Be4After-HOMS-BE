package com.beyond.homs.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleProductCategoryResponseDto {
    private Long domainId;

    private Long categoryId;

    private Long processId;

    private String productDomain;

    private String productCategory;

    private String manufacturingProcess;

    @Builder
    public SimpleProductCategoryResponseDto(Long domainId, Long categoryId, Long processId, String productDomain, String productCategory, String manufacturingProcess) {
        this.domainId = domainId;
        this.categoryId = categoryId;
        this.processId = processId;
        this.productDomain = productDomain;
        this.productCategory = productCategory;
        this.manufacturingProcess = manufacturingProcess;
    }
}
