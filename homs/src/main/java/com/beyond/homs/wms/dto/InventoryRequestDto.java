package com.beyond.homs.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryRequestDto {

    private Long warehouseId;

    private Long productId;

    private Long quantity;
}
