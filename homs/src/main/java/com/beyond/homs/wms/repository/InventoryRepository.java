package com.beyond.homs.wms.repository;

import com.beyond.homs.product.entity.Product;
import com.beyond.homs.wms.entity.Inventory;
import com.beyond.homs.wms.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByWarehouseAndProduct(Warehouse warehouse, Product product);
}