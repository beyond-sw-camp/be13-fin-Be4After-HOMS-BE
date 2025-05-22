package com.beyond.homs.product.repository;

import com.beyond.homs.product.entity.ProductFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductFileRepository extends JpaRepository<ProductFile, Long> {
    ProductFile findProductFileByProduct_ProductId(Long productProductId);
}
