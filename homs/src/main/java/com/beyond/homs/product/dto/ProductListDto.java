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

    private Long productMinQuantity;

    private Long productQuantity;

    private SimpleProductCategoryResponseDto category;

    public ProductListDto(Long productId, String productName, Long productMinQuantity, Long productQuantity, ProductCategory category) {
        this.productId = productId;
        this.productName = productName;
        this.productMinQuantity = productMinQuantity;
        this.productQuantity = productQuantity;
        SimpleProductCategoryResponseDto.SimpleProductCategoryResponseDtoBuilder builder = SimpleProductCategoryResponseDto.builder()
                .categoryId(category.getCategoryId());
        switch(category.getSortNo()){
            case 1:
                builder.productDomain(category.getCategoryName());
                break;
            case 2:
                builder.productDomain(category.getParent().getCategoryName());
                builder.productCategory(category.getCategoryName());
                break;
            case 3:
                builder.productDomain(category.getParent().getParent().getCategoryName());
                builder.productCategory(category.getParent().getCategoryName());
                builder.manufacturingProcess(category.getCategoryName());
                break;
        }
        this.category = builder.build();
    }

}
