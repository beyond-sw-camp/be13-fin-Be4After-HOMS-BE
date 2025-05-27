package com.beyond.homs.wms.service;

import com.beyond.homs.wms.dto.InventoryRequestDto;
import com.beyond.homs.wms.dto.WarehouseDetailDto;
import com.beyond.homs.wms.dto.WarehouseRequestDto;
import com.beyond.homs.wms.dto.WarehouseResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WarehouseService {
    // 창고 목록
    List<WarehouseResponseDto> getWarehouseList();

    // 창고 상세
    // 이 부분은 창고에 어떤 상품들이 들어있는지 알 필요가 있음 (추후에 추가)
    Page<WarehouseDetailDto> getWarehouseInventorys(
            Long warehouseId, String productName, String categoryName, Pageable pageable);

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
