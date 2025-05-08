package com.beyond.homs.product.dto;

import com.beyond.homs.product.entity.Product;
import lombok.Getter;

@Getter
public class ProductListDto {

    private final String productName;

    private final Long categoryId;

    public ProductListDto(Product product) {
        this.productName = product.getProductName();
        this.categoryId =  product.getCategory().getCategoryId();
    }
}
