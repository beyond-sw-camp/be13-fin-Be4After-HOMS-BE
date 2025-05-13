package com.beyond.homs.wms.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.wms.dto.WarehouseRequestDto;
import com.beyond.homs.wms.dto.WarehouseResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "창고 API", description = "창고 API 목록")
public interface WarehouseController {

    @Operation(summary = "창고 목록", description = "모든 창고 목록을 가져옵니다.")
    ResponseEntity<ResponseDto<List<WarehouseResponseDto>>> warehouseList();

    @Operation(summary = "창고 추가", description = "창고를 추가합니다.")
    ResponseEntity<ResponseDto<WarehouseResponseDto>> createWarehouse(
            @RequestBody WarehouseRequestDto requestDto);

    @Operation(summary = "창고 정보 수정", description = "창고 정보를 수정합니다.")
    ResponseEntity<ResponseDto<WarehouseResponseDto>> updateWarehouse(
            @PathVariable Long warehouseId,
            @RequestBody WarehouseRequestDto requestDto);

    @Operation(summary = "창고 삭제", description = "창고를 삭제합니다.")
    ResponseEntity<ResponseDto<Void>> deleteWarehouse(
            @PathVariable Long warehouseId);
}
