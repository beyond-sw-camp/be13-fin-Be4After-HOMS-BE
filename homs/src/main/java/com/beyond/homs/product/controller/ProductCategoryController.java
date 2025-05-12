package com.beyond.homs.product.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.product.dto.ProductCategoryListDto;
import com.beyond.homs.product.dto.ProductCategoryRequestDto;
import com.beyond.homs.product.dto.ProductCategoryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "상품카테고리관리 API", description = "상품카테고리관리 API 목록")
public interface ProductCategoryController {
    @Operation(summary = "상품 카테고리 목록 조회", description = "상품 카테고리 목록을 조회합니다.")
    ResponseEntity<ResponseDto<List<ProductCategoryListDto>>> productCategoryList();

    @Operation(summary = "상품 카테고리 등록", description = "상품 카테고리를 등록합니다.")
    ResponseEntity<ResponseDto<ProductCategoryResponseDto>> createProductCategory(
            @RequestBody ProductCategoryRequestDto productCategoryRequestDto);

    @Operation(summary = "상품 카테고리 수정", description = "상품 카테고리를 수정합니다.")
    ResponseEntity<ResponseDto<ProductCategoryResponseDto>> updateProductCategory(
            @PathVariable Long categoryId,
            @RequestBody ProductCategoryRequestDto productCategoryRequestDto);

    @Operation(summary = "상품 카테고리 제거", description = "상품 카테고리를 제거합니다.")
    ResponseEntity<ResponseDto<Void>> deleteProductCategory(
            @PathVariable Long categoryId);
}
