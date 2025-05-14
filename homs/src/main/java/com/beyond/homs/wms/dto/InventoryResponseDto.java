package com.beyond.homs.wms.dto;

import com.beyond.homs.wms.entity.InventoryId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InventoryResponseDto {
    private final InventoryId id;

    private final Long quantity;
}
