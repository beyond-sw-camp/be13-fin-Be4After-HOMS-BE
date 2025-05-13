package com.beyond.homs.product.repository;

import com.beyond.homs.product.dto.ProductListDto;
import com.beyond.homs.product.entity.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT NEW com.beyond.homs.product.dto.ProductListDto(p.productId, p.productName, p.category) " +
            "FROM Product p " +
            "WHERE (:name IS NULL OR p.productName LIKE %:name%) " +
            "AND (:category IS NULL OR p.category.categoryId = :category)")
    Page<ProductListDto> searchProduct(
            @Param("name") String name,
            @Param("category") Long category,
            Pageable pageable);
}
