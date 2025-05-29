package com.beyond.homs.order.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.order.dto.OrderItemRequestDto;
import com.beyond.homs.order.dto.OrderItemResponseDto;
import com.beyond.homs.order.dto.OrderItemSearchResponseDto;
import com.beyond.homs.order.dto.OrderRequestDto;
import com.beyond.homs.order.dto.OrderResponseDto;
import com.beyond.homs.order.entity.OrderItem;
import com.beyond.homs.product.data.ProductSearchOption;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "주문 상품 API", description = "주문 상품 API 목록")
public interface OrderItemController {

    @Operation(summary = "주문 상품 추가", description = "주문 상품을 추가합니다.")
    ResponseEntity<ResponseDto<List<OrderItemResponseDto>>> addOrderItem(
            @PathVariable Long orderId,
            @Valid @RequestBody List<OrderItemRequestDto> requestDto);

    @Operation(summary = "주문목록 상품 전체 조회", description = "모든 주문목록 상품을 조회합니다.")
    ResponseEntity<ResponseDto<Page<OrderItemSearchResponseDto>>> getAllOrderItems(
            @PathVariable Long orderId,
            @RequestParam(required = false) ProductSearchOption option,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, page = 0) Pageable pageable);

    @Operation(summary = "주문 상품 삭제", description = "주문 상품을 삭제합니다.")
    ResponseEntity<ResponseDto<Void>> deleteOrderItem(
            @PathVariable Long orderId,
            @RequestParam List<Long> productIds);

    @Operation(summary = "주문 상품 수정", description = "주문 상품을 수정합니다.")
    ResponseEntity<ResponseDto<OrderItemResponseDto>> updateOrderItem(
            @PathVariable("orderId") Long orderId,
            @PathVariable("productId") Long productId,
            @RequestParam Long quantity);

}
