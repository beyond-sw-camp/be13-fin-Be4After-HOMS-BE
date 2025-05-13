package com.beyond.homs.product.service;

import com.beyond.homs.product.dto.ProductListDto;
import com.beyond.homs.product.dto.ProductRequestDto;
import com.beyond.homs.product.dto.ProductResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductService {
    // 상품 목록 조회
    List<ProductListDto> getProducts();

    // 상품목록 상세 조회
    ProductResponseDto getProductDetail(Long productId);

    // 상품 기본 정보 등록
    @Transactional
    ProductResponseDto createProduct(ProductRequestDto requestDto);

    // 상품 기본 정보 수정
    @Transactional
    ProductResponseDto updateProduct(Long productId, ProductRequestDto requestDto);

    // 상품 삭제
    @Transactional
    void deleteProduct(Long productId);
}
