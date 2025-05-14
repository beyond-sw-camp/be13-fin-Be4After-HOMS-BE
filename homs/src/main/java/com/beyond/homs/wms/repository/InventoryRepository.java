package com.beyond.homs.wms.repository;

import com.beyond.homs.wms.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}