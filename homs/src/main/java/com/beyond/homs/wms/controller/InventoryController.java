package com.beyond.homs.wms.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.wms.dto.InventoryRequestDto;
import com.beyond.homs.wms.dto.InventoryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "재고 API", description = "재고 API 목록")
public interface InventoryController {

    @Operation(summary = "재고 추가 및 수정", description = "재고를 추가/수정 합니다.")
    ResponseEntity<ResponseDto<InventoryResponseDto>> createInventory(
            @RequestBody InventoryRequestDto requestDto);
}
