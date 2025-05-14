package com.beyond.homs.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WarehouseDetailDto {
    private Long warehouseId;

    private Long productId;

    private String categoryName;

    private String productName;

    private Long quantity;
}
