package com.beyond.homs.product.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.product.dto.ProductFileRequestDto;
import com.beyond.homs.product.dto.ProductFileResponseDto;
import com.beyond.homs.product.dto.ProductListDto;
import com.beyond.homs.product.dto.ProductRequestDto;
import com.beyond.homs.product.dto.ProductResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "상품관리 API", description = "상품관리 API 목록")
public interface ProductController {
    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회합니다.")
    ResponseEntity<ResponseDto<Page<ProductListDto>>> productList(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String productDomain,
            @RequestParam(required = false) String productCategory,
            @PageableDefault(size = 10, page = 0) Pageable pageable);

    @Operation(summary = "상품 상세 조회", description = "상품을 상세 조회합니다.")
    ResponseEntity<ResponseDto<ProductResponseDto>> productDetail(
            @PathVariable Long productId);

    @Operation(summary = "상품 모든 재고 조회", description = "상품의 모든 재고를 조회합니다.")
    ResponseEntity<ResponseDto<Long>> productQuantity(
            @PathVariable Long productId);

    @Operation(summary = "상품 기본 정보 등록", description = "상품 기본 정보를 등록합니다.")
    ResponseEntity<ResponseDto<ProductResponseDto>> createProduct(
            @RequestBody ProductRequestDto productRequestDto);

    @Operation(summary = "상품 기본 정보 수정", description = "상품 기본 정보를 수정합니다.")
    ResponseEntity<ResponseDto<ProductResponseDto>> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductRequestDto productRequestDto);

    @Operation(summary = "상품 제거", description = "상품을 제거합니다.")
    ResponseEntity<ResponseDto<Void>> deleteProduct(
            @PathVariable Long productId);

    // -------- 파일 부분 ----------
    @Operation(summary = "상품 파일 조회", description = "해당 상품의 파일을 조회합니다.")
    ResponseEntity<ResponseDto<ProductFileResponseDto>> productFileList(
            @PathVariable Long productId);

    @Operation(summary = "상품 파일 저장", description = "해당 상품 관련 파일을 저장합니다.")
    ResponseEntity<String> uploadProductFile(
            @RequestBody ProductFileRequestDto requestDto);

    @PutMapping("/files")
    ResponseEntity<String> updateProductFile(
            @RequestBody ProductFileRequestDto requestDto);
}