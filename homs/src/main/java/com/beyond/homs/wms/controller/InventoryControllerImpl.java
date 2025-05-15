package com.beyond.homs.wms.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.wms.dto.InventoryRequestDto;
import com.beyond.homs.wms.dto.InventoryResponseDto;
import com.beyond.homs.wms.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory")
public class InventoryControllerImpl implements InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/")
    @Override
    public ResponseEntity<ResponseDto<InventoryResponseDto>> createInventory(
            @RequestBody InventoryRequestDto requestDto){
        InventoryResponseDto inventory = inventoryService.setInventory(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ResponseDto<>(
                                HttpStatus.CREATED.value(),
                                "재고가 설정되었습니다.",
                                inventory
                        ));
    }
}
