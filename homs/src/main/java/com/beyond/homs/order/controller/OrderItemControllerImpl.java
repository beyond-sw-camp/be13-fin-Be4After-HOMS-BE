package com.beyond.homs.order.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.order.dto.OrderItemRequestDto;
import com.beyond.homs.order.dto.OrderItemResponseDto;
import com.beyond.homs.order.dto.OrderResponseDto;
import com.beyond.homs.order.service.OrderItemService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/orderitem")
public class OrderItemControllerImpl implements OrderItemController {
    private final OrderItemService orderItemService;

    @PostMapping("/{orderId}")
    @Override
    public ResponseEntity<ResponseDto<OrderItemResponseDto>> addOrderItem(
            @Valid @RequestBody OrderItemRequestDto requestDto) {
        OrderItemResponseDto dto = orderItemService.addOrderItem(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "주문 상품이 추가되었습니다.",
                        dto
                ));
    }

    @GetMapping("/{orderId}")
    @Override
    public ResponseEntity<ResponseDto<List<OrderItemResponseDto>>> getAllOrderItems(
            @PathVariable Long orderId) {
        List<OrderItemResponseDto> list = orderItemService.getOrderItems(orderId);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "모든 주문 상품을 성공적으로 조회했습니다.",
                list
        ));
    }

    @DeleteMapping("/{orderId}/out/{productId}")
    @Override
    public ResponseEntity<ResponseDto<Void>> deleteOrderItem(
            @PathVariable Long orderId,
            @PathVariable Long productId) {
        orderItemService.deleteOrderItem(orderId, productId);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "주문 상품이 성공적으로 삭제되었습니다.",
                null
        ));
    }

    @PutMapping("/{orderId}/renew/{productId}")
    @Override
    public ResponseEntity<ResponseDto<OrderItemResponseDto>> updateOrderItem(
            @PathVariable Long orderId,
            @PathVariable Long productId,
            @Valid @RequestBody OrderItemRequestDto requestDto) {
        OrderItemResponseDto dto = orderItemService.updateOrderItem(orderId, productId, requestDto);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "주문 상품이 성공적으로 수정되었습니다.",
                dto
        ));
    }
}
