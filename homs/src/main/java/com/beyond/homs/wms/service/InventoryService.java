package com.beyond.homs.wms.service;

import com.beyond.homs.wms.dto.InventoryRequestDto;
import com.beyond.homs.wms.dto.InventoryResponseDto;
import org.springframework.transaction.annotation.Transactional;

public interface InventoryService {
    // 창고에 재고 추가
    @Transactional
    InventoryResponseDto setInventory(InventoryRequestDto requestDto);
}
