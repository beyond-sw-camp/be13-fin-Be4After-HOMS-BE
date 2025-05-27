package com.beyond.homs.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class ProductCategoryResponseDto {

    private Long categoryId;

    private String categoryName;

    private int sortNo;

    private Long parentId;
}
