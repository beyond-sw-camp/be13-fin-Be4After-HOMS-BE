package com.beyond.homs.product.dto;

import com.beyond.homs.product.entity.Product;
import com.beyond.homs.product.entity.ProductCategory;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductListDto {

    private Long productId;

    private String productName;

    private Long productMinQuantity;

    private Long productQuantity;

    private SimpleProductCategoryResponseDto category;

    public ProductListDto(Long productId, String productName, Long productMinQuantity, Long productQuantity, ProductCategory category) {
        this.productId = productId;
        this.productName = productName;
        this.productMinQuantity = productMinQuantity;
        this.productQuantity = productQuantity;
        this.category = ProductCategoryMapper.mapToProductCategory(category);
    }
}
