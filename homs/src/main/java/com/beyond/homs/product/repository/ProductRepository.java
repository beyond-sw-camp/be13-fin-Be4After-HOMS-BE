package com.beyond.homs.product.repository;

import com.beyond.homs.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductByProductId(Long productId);
}
