package com.beyond.homs.product.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.product.dto.ProductListDto;
import com.beyond.homs.product.dto.ProductResponseDto;
import com.beyond.homs.product.dto.ProductRequestDto;
import com.beyond.homs.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/product/")
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    @GetMapping("/")
    @Override
    public ResponseEntity<ResponseDto<Page<ProductListDto>>> productList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long category,
            @PageableDefault(size = 10, page = 0) Pageable pageable){

        Page<ProductListDto> productList = productService.getProducts(name,category,pageable);
        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "모든 상품 목록을 불러왔습니다.",
                        productList
                ));
    }

    @GetMapping("/{productId}")
    @Override
    public ResponseEntity<ResponseDto<ProductResponseDto>> productDetail(
            @PathVariable Long productId){

        ProductResponseDto productDetail = productService.getProductDetail(productId);
        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "성공적으로 상품 상세 내용을 불러왔습니다.",
                        productDetail
                ));
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<ResponseDto<ProductResponseDto>> createProduct(
            @RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto product = productService.createProduct(productRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "상품이 성공적으로 등록되었습니다.",
                        product
                    ));
    }

    @PutMapping("/update/{productId}")
    @Override
    public ResponseEntity<ResponseDto<ProductResponseDto>> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductRequestDto productRequestDto){
        ProductResponseDto product = productService.updateProduct(productId, productRequestDto);
        return ResponseEntity.ok(
            new ResponseDto<>(
                HttpStatus.OK.value(),
                "상품 정보가 성공적으로 수정되었습니다.",
                product
            ));
    }

    @DeleteMapping("/delete/{productId}")
    @Override
    public ResponseEntity<ResponseDto<Void>> deleteProduct(
            @PathVariable Long productId){
        productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
