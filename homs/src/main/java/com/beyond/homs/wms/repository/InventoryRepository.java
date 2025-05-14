package com.beyond.homs.wms.repository;

import com.beyond.homs.wms.entity.Inventory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("SELECT SUM(i.quantity) " +
            "FROM Inventory i " +
            "WHERE i.product.productId = :productId ")
    Long sumQuantityByProductId(@Param("productId") Long productId);
}