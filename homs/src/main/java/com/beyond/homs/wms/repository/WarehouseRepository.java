package com.beyond.homs.wms.repository;

import com.beyond.homs.wms.dto.WarehouseDetailDto;
import com.beyond.homs.wms.entity.Warehouse;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    @Query("SELECT NEW com.beyond.homs.wms.dto.WarehouseDetailDto " +
            "(w.warehouseId,p.productId,c.categoryName,p.productName,i.quantity) " +
            "FROM Warehouse w " +
            "JOIN Inventory i ON i.id.warehouseId = w.warehouseId " +
            "JOIN Product p ON p.productId = i.id.productId " +
            "JOIN ProductCategory c ON c.categoryId = p.category.categoryId " +
            "WHERE (:warehouseId = w.warehouseId) " +
            "AND (:productName IS NULL OR p.productName LIKE %:productName%) " +
            "AND (:categoryName IS NULL OR c.categoryName LIKE %:categoryName%) ")
    Page<WarehouseDetailDto>searchInventory(
            @Param("warehouseId") Long warehouseId,
            @Param ("productName") String productName,
            @Param ("categoryName") String categoryName,
            Pageable pageable);
}
