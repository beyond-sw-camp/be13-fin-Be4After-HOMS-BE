package com.beyond.homs.product.repository;

import com.beyond.homs.product.entity.Product;
import com.beyond.homs.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findByParentIsNullOrderBySortNoAsc();

    List<ProductCategory> findByParent(ProductCategory parent);
}
