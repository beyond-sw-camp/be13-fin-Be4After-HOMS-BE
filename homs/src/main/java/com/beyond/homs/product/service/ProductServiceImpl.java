package com.beyond.homs.product.service;

import com.beyond.homs.product.dto.ProductListDto;
import com.beyond.homs.product.dto.ProductRequestDto;
import com.beyond.homs.product.dto.ProductResponseDto;
import com.beyond.homs.product.entity.Product;
import com.beyond.homs.product.entity.ProductCategory;
import com.beyond.homs.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    // 상품 목록 조회
    @Override
    public List<ProductListDto> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductListDto::new)
                .collect(Collectors.toList());
    }

    // 상품목록 상세 조회
    @Override
    public ProductResponseDto getProductDetail(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));

        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productFeature(product.getProductFeature())
                .productUsage(product.getProductUsage())
                .build();
    }

    // 상품 기본 정보 등록
    @Transactional
    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto){

        Product product = Product.builder()
                .productName(requestDto.getProductName())
                .productFeature(requestDto.getProductFeature())
                .productUsage(requestDto.getProductUsage())
                // .category(requestDto.getCategoryId())
                .build();
        Product saveProduct = productRepository.save(product);

        return ProductResponseDto.builder()
                .productId(saveProduct.getProductId())
                .productName(saveProduct.getProductName())
                .productFeature(saveProduct.getProductFeature())
                .productUsage(saveProduct.getProductUsage())
                // .category(saveProduct.getCategory())
                .build();
    }

    // 상품 기본 정보 수정
    @Transactional
    @Override
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto requestDto){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));

        ProductCategory category = productRepository.findProductByProductId(
                requestDto.getCategoryId()).getFirst().getCategory();

        product.update(requestDto,category);
        productRepository.save(product);

        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productFeature(product.getProductFeature())
                .productUsage(product.getProductUsage())
                .build();
    }

    // 상품 삭제
    @Transactional
    @Override
    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }
}
