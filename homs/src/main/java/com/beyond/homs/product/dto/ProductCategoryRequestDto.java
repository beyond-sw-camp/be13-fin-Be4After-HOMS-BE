package com.beyond.homs.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductCategoryRequestDto {
    private String categoryName;

    private int sortNo;

    private Long parentId;
}
