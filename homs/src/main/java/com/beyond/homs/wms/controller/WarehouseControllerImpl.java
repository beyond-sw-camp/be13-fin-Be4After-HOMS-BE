package com.beyond.homs.wms.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.wms.dto.WarehouseRequestDto;
import com.beyond.homs.wms.dto.WarehouseResponseDto;
import com.beyond.homs.wms.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/warehouse")
public class WarehouseControllerImpl implements WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping("/")
    @Override
    public ResponseEntity<ResponseDto<List<WarehouseResponseDto>>> warehouseList(){
        List<WarehouseResponseDto> warehouseList = warehouseService.getWarehouseList();
        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "모든 창고 목록을 불러왔습니다.",
                        warehouseList
                ));
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<ResponseDto<WarehouseResponseDto>> createWarehouse(
            @RequestBody WarehouseRequestDto requestDto){
        WarehouseResponseDto warehouse = warehouseService.createWarehouse(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    new ResponseDto<>(
                            HttpStatus.CREATED.value(),
                            "창고가 추가되었습니다.",
                            warehouse
                ));
    }

    @PutMapping("/update/{warehouseId}")
    @Override
    public ResponseEntity<ResponseDto<WarehouseResponseDto>> updateWarehouse(
            @PathVariable Long warehouseId,
            @RequestBody WarehouseRequestDto requestDto){
        WarehouseResponseDto warehouse = warehouseService.updateWarehouse(warehouseId,requestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseDto<>(
                                HttpStatus.OK.value(),
                                "창고 정보가 수정되었습니다.",
                                warehouse
                        ));
    }

    @DeleteMapping("/delete/{warehouseId}")
    @Override
    public ResponseEntity<ResponseDto<Void>> deleteWarehouse(
            @PathVariable Long warehouseId){
        warehouseService.deleteWarehouse(warehouseId);

        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "창고가 성공적으로 삭제되었습니다.",
                        null
                ));
    }
}
