package com.beyond.homs.product.service;

import com.beyond.homs.product.dto.ProductCategoryListDto;
import com.beyond.homs.product.dto.ProductCategoryRequestDto;
import com.beyond.homs.product.dto.ProductCategoryResponseDto;
import com.beyond.homs.product.entity.ProductCategory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public interface ProductCategoryService {
    // 상품 카테고리 목록 조회
    List<ProductCategoryListDto> getProductCategories();

    // 상품 카테고리 등록
    @Transactional
    ProductCategoryResponseDto createProductCategory(ProductCategoryRequestDto requestDto);

    // 상품 카테고리 수정
    @Transactional
    ProductCategoryResponseDto updateProductCategory(Long categoryId, ProductCategoryRequestDto requestDto);

    // 상품 카테고리 삭제
    @Transactional
    void deleteProductCategory(Long categoryId);
}
