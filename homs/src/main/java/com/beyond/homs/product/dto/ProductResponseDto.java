package com.beyond.homs.product.dto;

import com.beyond.homs.product.entity.ProductCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private Long productId;

    private String productName;

    private String productFeature;

    private String productUsage;

    private Long productMinQuantity;

    private SimpleProductCategoryResponseDto category;

    @Builder
    public ProductResponseDto(Long productId, String productName, String productFeature, String productUsage, Long productMinQuantity, ProductCategory category) {
        this.productId = productId;
        this.productName = productName;
        this.productFeature = productFeature;
        this.productUsage = productUsage;
        this.productMinQuantity = productMinQuantity;
        SimpleProductCategoryResponseDto.SimpleProductCategoryResponseDtoBuilder builder = SimpleProductCategoryResponseDto.builder();
        switch(category.getSortNo()){
            case 1:
                builder.domainId(category.getCategoryId());
                builder.productDomain(category.getCategoryName());
                break;
            case 2:
                builder.domainId(category.getParent().getCategoryId());
                builder.productDomain(category.getParent().getCategoryName());
                builder.categoryId(category.getCategoryId());
                builder.productCategory(category.getCategoryName());
                break;
            case 3:
                builder.domainId(category.getParent().getParent().getCategoryId());
                builder.productDomain(category.getParent().getParent().getCategoryName());
                builder.categoryId(category.getParent().getCategoryId());
                builder.productCategory(category.getParent().getCategoryName());
                builder.processId(category.getCategoryId());
                builder.manufacturingProcess(category.getCategoryName());
                break;
        }
        this.category = builder.build();
    }
}
