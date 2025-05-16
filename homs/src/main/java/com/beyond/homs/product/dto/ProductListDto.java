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

    private Long productQuantity;

    private SimpleProductCategoryResponseDto category;

    public ProductListDto(Long productId, String productName, Long productQuantity, ProductCategory category) {
        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
        SimpleProductCategoryResponseDto.SimpleProductCategoryResponseDtoBuilder builder = SimpleProductCategoryResponseDto.builder()
                .categoryId(category.getCategoryId())
                .processName(category.getCategoryName());

        // 하위 카테고리 있으면 할당
        if (category.getParent() != null) {
            builder.categoryName(category.getParent().getCategoryName());
            if (category.getParent().getParent() != null) {
                builder.upperCategoryName(category.getParent().getParent().getCategoryName());
            }
        }

        this.category = builder.build();
    }
}
