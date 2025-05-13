package com.beyond.homs.product.service;

import com.beyond.homs.common.exception.exceptions.CustomException;
import com.beyond.homs.common.exception.messages.ExceptionMessage;
import com.beyond.homs.product.dto.ProductListDto;
import com.beyond.homs.product.dto.ProductRequestDto;
import com.beyond.homs.product.dto.ProductResponseDto;
import com.beyond.homs.product.entity.Product;
import com.beyond.homs.product.entity.ProductCategory;
import com.beyond.homs.product.repository.ProductCategoryRepository;
import com.beyond.homs.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    // 상품 목록 조회
    @Override
    public Page<ProductListDto> getProducts(String name, Long category, Pageable pageable) {
        return productRepository.searchProduct(name,category,pageable);
    }

    // 상품목록 상세 조회
    @Override
    public ProductResponseDto getProductDetail(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ExceptionMessage.PRODUCT_NOT_FOUND));

        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productFeature(product.getProductFeature())
                .productUsage(product.getProductUsage())
                .category(product.getCategory())
                .build();
    }

    // 상품 기본 정보 등록
    @Transactional
    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto){

        ProductCategory productCategory = productCategoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.CATEGORY_NOT_FOUND));

        Product product = Product.builder()
                .productName(requestDto.getProductName())
                .productFeature(requestDto.getProductFeature())
                .productUsage(requestDto.getProductUsage())
                .category(productCategory)
                .build();
        Product saveProduct = productRepository.save(product);

        return ProductResponseDto.builder()
                .productId(saveProduct.getProductId())
                .productName(saveProduct.getProductName())
                .productFeature(saveProduct.getProductFeature())
                .productUsage(saveProduct.getProductUsage())
                .category(saveProduct.getCategory())
                .build();
    }

    // 상품 기본 정보 수정
    @Transactional
    @Override
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto requestDto){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ExceptionMessage.PRODUCT_NOT_FOUND));

        ProductCategory productCategory = productCategoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.CATEGORY_NOT_FOUND));

        product.update(requestDto,productCategory);
        productRepository.save(product);

        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productFeature(product.getProductFeature())
                .productUsage(product.getProductUsage())
                .category(product.getCategory())
                .build();
    }

    // 상품 삭제
    @Transactional
    @Override
    public void deleteProduct(Long productId){

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ExceptionMessage.PRODUCT_NOT_FOUND));

        productRepository.deleteById(productId);
    }
}
