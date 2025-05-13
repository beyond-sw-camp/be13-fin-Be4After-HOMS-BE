package com.beyond.homs.wms.service;

import com.beyond.homs.common.exception.exceptions.CustomException;
import com.beyond.homs.common.exception.messages.ExceptionMessage;
import com.beyond.homs.wms.dto.WarehouseRequestDto;
import com.beyond.homs.wms.dto.WarehouseResponseDto;
import com.beyond.homs.wms.entity.Warehouse;
import com.beyond.homs.wms.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;

    // 모든 창고 리스트 가져오기
    @Override
    public List<WarehouseResponseDto> getWarehouseList() {
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        return warehouseList.stream()
                .map(warehouse -> new WarehouseResponseDto(
                        warehouse.getWarehouseId(),
                        warehouse.getWarehouseName(),
                        warehouse.getLocation()
                ))
                .collect(Collectors.toList());
    }

    // 창고 상세
    // 이 부분은 창고에 어떤 상품들이 들어있는지 알 필요가 있음 (추후에 추가)

    // 창고 추가
    @Transactional
    @Override
    public WarehouseResponseDto createWarehouse(WarehouseRequestDto requestDto) {

        Warehouse warehouse = Warehouse.builder()
                .warehouseName(requestDto.getWarehouseName())
                .location(requestDto.getLocation())
                .build();
        Warehouse saveWarehouse = warehouseRepository.save(warehouse);

        return WarehouseResponseDto.builder()
                .warehouseId(saveWarehouse.getWarehouseId())
                .warehouseName(saveWarehouse.getWarehouseName())
                .location(saveWarehouse.getLocation())
                .build();
    }

    // 창고 수정
    @Transactional
    @Override
    public WarehouseResponseDto updateWarehouse(Long warehouseId, WarehouseRequestDto requestDto) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new CustomException(ExceptionMessage.WAREHOUSE_NOT_FOUND));

        // 각 값이 빈값이면 이전값을 채워넣음
        if (requestDto.getWarehouseName() == null) {
            requestDto.setWarehouseName(warehouse.getWarehouseName());
        }
        if (requestDto.getLocation() == null) {
            requestDto.setLocation(warehouse.getLocation());
        }

        warehouse.update(
                requestDto.getWarehouseName(),
                requestDto.getLocation());
        warehouseRepository.save(warehouse);

        return WarehouseResponseDto.builder()
                .warehouseId(warehouse.getWarehouseId())
                .warehouseName(warehouse.getWarehouseName())
                .location(warehouse.getLocation())
                .build();
    }

    // 창고 삭제
    @Transactional
    @Override
    public void deleteWarehouse(Long warehouseId) {
        Warehouse post = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new CustomException(ExceptionMessage.WAREHOUSE_NOT_FOUND));

        warehouseRepository.deleteById(warehouseId);
    }
}
