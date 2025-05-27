package com.beyond.homs.order.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.order.dto.OrderRequestDto;
import com.beyond.homs.order.dto.OrderResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "주문 API", description = "주문 API 목록")
public interface OrderController {

    @Operation(summary = "주문 생성", description = "주문을 생성합니다.")
    ResponseEntity<ResponseDto<OrderResponseDto>> createOrder();

    @Operation(summary = "주문 단건 조회", description = "주문 ID로 단건 조회합니다.")
    ResponseEntity<ResponseDto<OrderResponseDto>> getOrder(
            @PathVariable("orderId") Long orderId);

    @Operation(summary = "주문 전체 조회", description = "모든 주문 목록을 조회합니다.")
    ResponseEntity<ResponseDto<List<OrderResponseDto>>> getAllOrders();

    @Operation(summary = "주문 수정", description = "주문 정보를 수정합니다.")
    ResponseEntity<ResponseDto<OrderResponseDto>> updateOrder(
            @PathVariable("orderId") Long orderId,
            @Valid @RequestBody OrderRequestDto requestDto);

    @Operation(summary = "주문 삭제", description = "주문을 삭제합니다.")
    ResponseEntity<ResponseDto<Void>> deleteOrder(
            @PathVariable("orderId") Long orderId);

    @Operation(summary = "주문 코드로 조회", description = "orderCode로 단건 조회합니다.")
    ResponseEntity<ResponseDto<OrderResponseDto>> getOrderByCode(
            @PathVariable("orderCode") String orderCode
    );

    @Operation(summary = "사용자별 주문 조회", description = "userId에 속한 모든 주문을 조회합니다.")
    ResponseEntity<ResponseDto<List<OrderResponseDto>>> getOrdersByUser(
            @PathVariable("userId") Long userId
    );

    @Operation(summary = "하위 주문 조회", description = "parentOrderId의 모든 하위 주문을 조회합니다.")
    ResponseEntity<ResponseDto<List<OrderResponseDto>>> getChildOrders(
            @PathVariable("parentOrderId") Long parentOrderId
    );

}
