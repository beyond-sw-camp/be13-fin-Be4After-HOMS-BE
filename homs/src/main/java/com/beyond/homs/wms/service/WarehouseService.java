package com.beyond.homs.wms.service;

import com.beyond.homs.wms.dto.WarehouseRequestDto;
import com.beyond.homs.wms.dto.WarehouseResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WarehouseService {
    // 창고 목록
    List<WarehouseResponseDto> getWarehouseList();

    // 창고 추가
    @Transactional
    WarehouseResponseDto createWarehouse(WarehouseRequestDto requestDto);

    // 창고 수정
    @Transactional
    WarehouseResponseDto updateWarehouse(Long warehouseId, WarehouseRequestDto requestDto);

    // 창고 삭제
    @Transactional
    void deleteWarehouse(Long warehouseId);
}
