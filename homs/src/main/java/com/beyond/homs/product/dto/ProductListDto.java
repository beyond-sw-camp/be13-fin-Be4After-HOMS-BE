package com.beyond.homs.product.dto;

import com.beyond.homs.product.entity.Product;
import com.beyond.homs.product.entity.ProductCategory;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductListDto {

    private Long productId;

    private String productName;

    private SimpleProductCategoryResponseDto category;

    public ProductListDto(Long productId, String productName, ProductCategory category) {
        this.productId = productId;
        this.productName = productName;
        this.category = SimpleProductCategoryResponseDto.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }
}
