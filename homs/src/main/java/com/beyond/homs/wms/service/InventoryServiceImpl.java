package com.beyond.homs.wms.service;

import com.beyond.homs.common.exception.exceptions.CustomException;
import com.beyond.homs.common.exception.messages.ExceptionMessage;
import com.beyond.homs.product.entity.Product;
import com.beyond.homs.product.repository.ProductRepository;
import com.beyond.homs.wms.dto.InventoryRequestDto;
import com.beyond.homs.wms.dto.InventoryResponseDto;
import com.beyond.homs.wms.entity.Inventory;
import com.beyond.homs.wms.entity.Warehouse;
import com.beyond.homs.wms.repository.InventoryRepository;
import com.beyond.homs.wms.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    // 창고에 재고 설정
    @Transactional
    @Override
    public InventoryResponseDto setInventory(InventoryRequestDto requestDto) {

        Warehouse warehouse = warehouseRepository.findById(requestDto.getWarehouseId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.WAREHOUSE_NOT_FOUND));
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.PRODUCT_NOT_FOUND));

        Inventory inventory = Inventory.builder()
                .warehouse(warehouse)
                .product(product)
                .quantity(requestDto.getQuantity())
                .build();
        Inventory saveInventory = inventoryRepository.save(inventory);

        return InventoryResponseDto.builder()
                .id(saveInventory.getId())
                .quantity(saveInventory.getQuantity())
                .build();
    }
}
