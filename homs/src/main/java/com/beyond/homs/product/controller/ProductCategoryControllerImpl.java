package com.beyond.homs.product.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.product.dto.ProductCategoryListDto;
import com.beyond.homs.product.dto.ProductCategoryRequestDto;
import com.beyond.homs.product.dto.ProductCategoryResponseDto;
import com.beyond.homs.product.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/productCategory/")
public class ProductCategoryControllerImpl implements ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    //상품 카테고리 목록 조회
    @GetMapping("/")
    @Override
    public ResponseEntity<ResponseDto<List<ProductCategoryListDto>>> productCategoryList() {

        List<ProductCategoryListDto> productCategoryList = productCategoryService.getProductCategories();
        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "상품 카테고리 목록을 불러왔습니다.",
                        productCategoryList
                ));
    }

    //상품 카테고리 등록
    @PostMapping("/create")
    @Override
    public ResponseEntity<ResponseDto<ProductCategoryResponseDto>> createProductCategory(
            @RequestBody ProductCategoryRequestDto productCategoryRequestDto) {
        ProductCategoryResponseDto productCategory = productCategoryService.createProductCategory(productCategoryRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ResponseDto<>(
                                HttpStatus.CREATED.value(),
                                "상품 카테고리가 등록되었습니다.",
                                productCategory
                        ));
    }

    //상품 카테고리 수정
    @PutMapping("/update/{categoryId}")
    @Override
    public ResponseEntity<ResponseDto<ProductCategoryResponseDto>> updateProductCategory(
            @PathVariable Long categoryId,
            @RequestBody ProductCategoryRequestDto productCategoryRequestDto) {
        ProductCategoryResponseDto productCategory = productCategoryService.updateProductCategory(categoryId, productCategoryRequestDto);
        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "상품 카테고리가 수정되었습니다.",
                        productCategory
                ));
    }

    //상품 카테고리 제거
    @DeleteMapping("/delete/{categoryId}")
    @Override
    public ResponseEntity<ResponseDto<Void>> deleteProductCategory(
            @PathVariable Long categoryId) {
        productCategoryService.deleteProductCategory(categoryId);

        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "상품 카테고리가 삭제되었습니다.",
                        null
                ));
    }
}
