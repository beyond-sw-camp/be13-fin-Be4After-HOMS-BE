package com.beyond.homs.order.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.order.data.OrderSearchOption;
import com.beyond.homs.order.data.OrderStatusEnum;

import com.beyond.homs.order.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "주문 API", description = "주문 API 목록")
public interface OrderController {

    @Operation(summary = "주문 생성", description = "주문을 생성합니다.")
    ResponseEntity<ResponseDto<OrderResponseDto>> createOrder();

    @Operation(summary = "주문 단건 조회", description = "주문 ID로 단건 조회합니다.")
    ResponseEntity<ResponseDto<OrderResponseDto>> getOrder(
            @PathVariable("orderId") Long orderId);

    @Operation(summary = "주문 전체 조회", description = "모든 주문 목록을 조회합니다.")
    ResponseEntity<ResponseDto<Page<OrderResponseDto>>> getAllOrders(
            @RequestParam(required = false) OrderSearchOption option,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, page = 0) Pageable pageable);

    @Operation(summary = "주문 수정", description = "주문 정보를 수정합니다.")
    ResponseEntity<ResponseDto<OrderResponseDto>> updateOrder(
            @PathVariable("orderId") Long orderId,
            @Valid @RequestBody OrderRequestDto requestDto);

    @Operation(summary = "주문 삭제", description = "주문을 삭제합니다.")
    ResponseEntity<ResponseDto<Void>> deleteOrder(
            @PathVariable("orderId") Long orderId);

    @Operation(summary = "승인 여부 설정", description = "승인 여부를 설정합니다")
    ResponseEntity<ResponseDto<Void>> setApprove(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderApproveRequestDto requestDto);

    @Operation(summary = "납품일 업데이트", description = "납품일을 업데이트합니다")
    ResponseEntity<ResponseDto<Void>> updateDate(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderDateRequestDto requestDto);

    @Operation(summary = "배송상태 업데이트", description = "배송상태를 업데이트합니다")
    ResponseEntity<ResponseDto<Void>> updateStatus(
            @PathVariable Long orderId,
            @Valid @RequestParam OrderStatusEnum requestStatus);


    @Operation(summary = "주문 코드로 조회", description = "orderCode로 단건 조회합니다.")
    ResponseEntity<ResponseDto<OrderResponseDto>> getOrderByCode(
            @PathVariable("orderCode") String orderCode
    );

    @Operation(summary = "하위 주문 조회", description = "parentOrderId의 모든 하위 주문을 조회합니다.")
    ResponseEntity<ResponseDto<List<OrderResponseDto>>> getChildOrders(
            @PathVariable("parentOrderId") Long parentOrderId
    );

    @Operation(summary = "주문별 배송목록 조회", description = "주문건 별로 배송 정보를 조회합니다.")
    ResponseEntity<ResponseDto<List<OrderDeliveryResponseDTO>>> getDeliveryInfo();

    @Operation(summary = "사용자별 배송목록 조회", description = "사용자별 배송 정보를 조회합니다.")
    ResponseEntity<ResponseDto<List<OrderDeliveryResponseDTO>>> getDeliveryInfoByUser(Long userId);

    @Operation(summary = "하위 주문 생성", description = "클레임에 대한 자식 주문을 생성합니다.")
    ResponseEntity<ResponseDto<Long>> getDeliveryInfoByUser(
            @RequestBody OrderParentRequestDto requestDto);
}
