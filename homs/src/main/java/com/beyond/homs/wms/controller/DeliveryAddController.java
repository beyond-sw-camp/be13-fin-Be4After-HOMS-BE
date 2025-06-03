package com.beyond.homs.wms.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.wms.dto.DeliveryAddRequestDto;
import com.beyond.homs.wms.dto.DeliveryAddResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "배송지 API", description = "배송지 API 목록")
public interface DeliveryAddController {
    @Operation(summary = "배송지 목록", description = "모든 배송지 목록을 가져옵니다.")
    ResponseEntity<ResponseDto<List<DeliveryAddResponseDto>>> deliveryAddList();

    @Operation(summary = "회사별 배송지 목록", description = "회사별 배송지 목록을 가져옵니다.")
    ResponseEntity<ResponseDto<List<DeliveryAddResponseDto>>> deliveryAddListByCompany(
            @PathVariable Long companyId);

    @Operation(summary = "배송지 추가", description = "배송지를 추가합니다.")
    ResponseEntity<ResponseDto<DeliveryAddResponseDto>> createDeliveryAdd(
            @RequestBody DeliveryAddRequestDto requestDto);

    @Operation(summary = "배송지 정보 수정", description = "배송지 정보를 수정합니다.")
    ResponseEntity<ResponseDto<DeliveryAddResponseDto>> updateDeliveryAdd(
            @PathVariable Long DeliveryAddId,
            @RequestBody DeliveryAddRequestDto requestDto);

    @Operation(summary = "배송지 삭제", description = "배송지를 삭제합니다.")
    ResponseEntity<ResponseDto<Void>> deleteDeliveryAdd(
            @PathVariable Long DeliveryAddId);
}
