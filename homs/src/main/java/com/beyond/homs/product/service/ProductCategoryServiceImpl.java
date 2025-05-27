package com.beyond.homs.product.service;

import com.beyond.homs.product.dto.ProductCategoryListDto;
import com.beyond.homs.product.dto.ProductCategoryRequestDto;
import com.beyond.homs.product.dto.ProductCategoryResponseDto;
import com.beyond.homs.product.dto.ProductResponseDto;
import com.beyond.homs.product.entity.Product;
import com.beyond.homs.product.entity.ProductCategory;
import com.beyond.homs.product.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService{
    private final ProductCategoryRepository productCategoryRepository;

    // 상품 카테고리 조회
    @Override
    public List<ProductCategoryListDto> getProductCategories() {
        //JPA는 메서드 이름을 보면 내부에서 자동으로 JPQL 쿼리를 만들어 실행 (최상위 루트 카테고리만 가져옴)
        List<ProductCategory> rootCategories = productCategoryRepository.findByParentIsNullOrderBySortNoAsc();

        return rootCategories.stream()
                .map(ProductCategoryListDto::of)
                .collect(Collectors.toList());
    }

    // 상품 카테고리 등록
    @Override
    public ProductCategoryResponseDto createProductCategory(ProductCategoryRequestDto requestDto) {
        ProductCategory parent = null;

        if (requestDto.getParentId() != null) {
            parent = productCategoryRepository.findById(requestDto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리가 존재하지 않습니다."));
        }

        ProductCategory productCategory = ProductCategory.builder()
                .categoryName(requestDto.getCategoryName())
                .sortNo(requestDto.getSortNo())
                .parent(parent)
                .build();

        ProductCategory saved = productCategoryRepository.save(productCategory);

        return ProductCategoryResponseDto.builder()
                .categoryId(saved.getCategoryId())
                .categoryName(saved.getCategoryName())
                .sortNo(saved.getSortNo())
                .parentId(saved.getParent() != null ? saved.getParent().getCategoryId() : null)
                .build();
    }

    // 상품 카테고리 수정
    @Override
    public ProductCategoryResponseDto updateProductCategory(Long categoryId, ProductCategoryRequestDto requestDto) {
        ProductCategory productCategory = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("해당 카테고리가 존재하지 않습니다."));

        productCategory.updateProductCategory(requestDto);
        productCategoryRepository.save(productCategory);

        return ProductCategoryResponseDto.builder()
                .categoryId(productCategory.getCategoryId())
                .categoryName(productCategory.getCategoryName())
                .sortNo(productCategory.getSortNo())
                .parentId(productCategory.getParent().getCategoryId())
                .build();
    }

    // 상품 카테고리 삭제
    @Transactional
    @Override
    public void deleteProductCategory(Long categoryId) {
        ProductCategory category = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));

        // 자식 카테고리 조회
        List<ProductCategory> children = productCategoryRepository.findByParent(category);

        // 자식이 존재하면 먼저 삭제
        if (!children.isEmpty()) {
            productCategoryRepository.deleteAll(children);
        }

        // 부모 카테고리 삭제 or 자식인 경우 단독 삭제
        productCategoryRepository.delete(category);
    }
}
