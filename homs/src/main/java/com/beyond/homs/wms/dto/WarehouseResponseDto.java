package com.beyond.homs.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class WarehouseResponseDto {
    private final Long warehouseId;

    private final String warehouseName;

    private final String location;
}
