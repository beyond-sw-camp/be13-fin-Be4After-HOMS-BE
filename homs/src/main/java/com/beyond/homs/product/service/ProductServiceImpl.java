package com.beyond.homs.product.service;

import com.beyond.homs.common.exception.exceptions.CustomException;
import com.beyond.homs.common.exception.messages.ExceptionMessage;
import com.beyond.homs.product.dto.ProductFileRequestDto;
import com.beyond.homs.product.dto.ProductFileResponseDto;
import com.beyond.homs.product.dto.ProductListDto;
import com.beyond.homs.product.dto.ProductRequestDto;
import com.beyond.homs.product.dto.ProductResponseDto;
import com.beyond.homs.product.entity.Product;
import com.beyond.homs.product.entity.ProductCategory;
import com.beyond.homs.product.entity.ProductFile;
import com.beyond.homs.product.repository.ProductCategoryRepository;
import com.beyond.homs.product.repository.ProductFileRepository;
import com.beyond.homs.product.repository.ProductRepository;
import com.beyond.homs.wms.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductFileRepository productFileRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final InventoryRepository inventoryRepository;

    // 상품 목록 조회
    @Override
    public Page<ProductListDto> getProducts(String name, String productDomain, String productCategory, Pageable pageable) {
        return productRepository.searchProductWithInventory(name,productDomain,productCategory,pageable);
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

    // 해당 상품의 총 재고 수량
    @Override
    public Long getProductQuantity(Long productId){
        return inventoryRepository.sumQuantityByProductId(productId);
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

    /*------ 파일 관련 --------*/
    // 파일 조회
    @Override
    public ProductFileResponseDto getProductFile(Long productId){

        ProductFile productFile = productFileRepository.findProductFileByProduct_ProductId(productId);

        return ProductFileResponseDto.builder()
                .id(productFile.getId())
                .s3Image(productFile.getS3Image())
                .s3Msds(productFile.getS3Msds())
                .s3Tds1(productFile.getS3Tds1())
                .s3Tds2(productFile.getS3Tds2())
                .s3Property(productFile.getS3Property())
                .s3Guide(productFile.getS3Guide())
                .build();
    }

    // 파일 저장
    @Transactional
    @Override
    public void uploadProductFile(ProductFileRequestDto requestDto){

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.PRODUCT_NOT_FOUND));

        ProductFile productFile = ProductFile.builder()
                .product(product)
                .s3Image(requestDto.getS3Image())
                .s3Msds(requestDto.getS3Msds())
                .s3Tds1(requestDto.getS3Tds1())
                .s3Tds2(requestDto.getS3Tds2())
                .s3Property(requestDto.getS3Property())
                .s3Guide(requestDto.getS3Guide())
                .build();

        productFileRepository.save(productFile);
    }

    // 파일 수정
    @Transactional
    @Override
    public void updateProductFile(ProductFileRequestDto requestDto){
        ProductFile productFile = productFileRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.PRODUCT_NOT_FOUND));

        productFile.update(requestDto);
        productFileRepository.save(productFile);
    }

    // 파일 삭제
    // @Transactional
    // @Override
    // public void deleteProductFile(ProductFileRequestDto requestDto)
    // {
    //
    // }
}
