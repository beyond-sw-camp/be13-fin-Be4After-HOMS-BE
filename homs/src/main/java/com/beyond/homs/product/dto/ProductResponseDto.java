package com.beyond.homs.product.dto;

import com.beyond.homs.product.entity.Product;
import com.beyond.homs.product.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private Long productId;

    private String productName;

    private String productFeature;

    private String productUsage;

    private SimpleProductCategoryResponseDto category;

    @Builder
    public ProductResponseDto(Long productId, String productName, String productFeature, String productUsage, ProductCategory category) {
        this.productId = productId;
        this.productName = productName;
        this.productFeature = productFeature;
        this.productUsage = productUsage;
        SimpleProductCategoryResponseDto.SimpleProductCategoryResponseDtoBuilder builder = SimpleProductCategoryResponseDto.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName());

        // 하위 카테고리 있으면 할당
        if (category.getParent() != null) {
            builder.upperCategoryName(category.getParent().getCategoryName());
            if (category.getParent().getParent() != null) {
                builder.processName(category.getParent().getParent().getCategoryName());
            }
        }

        this.category = builder.build();
    }
}
