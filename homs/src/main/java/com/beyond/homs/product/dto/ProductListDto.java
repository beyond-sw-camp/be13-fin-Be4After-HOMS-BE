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
                .categoryName(category.getCategoryName());

        // 부모 카테고리가 있는 경우에만 upperCategoryName 설정
        if (category.getParent() != null) {
            builder.upperCategoryName(category.getParent().getCategoryName());
        } else {
            builder.upperCategoryName(null); // 부모가 없으면 null 설정
        }

        this.category = builder.build();
    }
}
