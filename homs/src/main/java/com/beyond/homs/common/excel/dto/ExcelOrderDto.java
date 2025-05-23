package com.beyond.homs.common.excel.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExcelOrderDto {
    private Long productId;

    private String productDomain;

    private String productCategory;

    private String productName;

    private Long productQuantity;

    @Builder
    public ExcelOrderDto(Long productId, String productDomain, String productCategory, String productName, Long productQuantity) {
        this.productId = productId;
        this.productDomain = productDomain;
        this.productCategory = productCategory;
        this.productName = productName;
        this.productQuantity = productQuantity;
    }

}
