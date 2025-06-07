package com.beyond.homs.product.dto;

import com.beyond.homs.product.entity.ProductCategory;

public class ProductCategoryMapper {
    public static SimpleProductCategoryResponseDto mapToProductCategory(ProductCategory category) {
        if (category == null) {
            return null; // 또는 기본값 설정
        }

        SimpleProductCategoryResponseDto.SimpleProductCategoryResponseDtoBuilder builder = SimpleProductCategoryResponseDto.builder()
                .categoryId(category.getCategoryId());

        switch (category.getSortNo()) {
            case 1:
                builder.productDomain(category.getCategoryName());
                break;
            case 2:
                if (category.getParent() != null) {
                    builder.productDomain(category.getParent().getCategoryName());
                }
                builder.productCategory(category.getCategoryName());
                break;
            case 3:
                ProductCategory parent = category.getParent();
                if (parent != null) {
                    ProductCategory grandParent = parent.getParent();
                    if (grandParent != null) {
                        builder.productDomain(grandParent.getCategoryName());
                    }
                    builder.productCategory(parent.getCategoryName());
                }
                builder.manufacturingProcess(category.getCategoryName());
                break;
            default:
                break;
        }
        return builder.build();
    }
}
