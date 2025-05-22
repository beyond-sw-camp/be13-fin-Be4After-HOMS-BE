package com.beyond.homs.order.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.order.dto.OrderRequestDto;
import com.beyond.homs.order.dto.OrderResponseDto;
import com.beyond.homs.order.service.OrderService;
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
@RequestMapping("/api/v1/order")
public class OrderControllerImpl implements OrderController {
    private final OrderService orderService;

    @PostMapping("/")
    @Override
    public ResponseEntity<ResponseDto<OrderResponseDto>> createOrder(
            @Valid @RequestBody OrderRequestDto requestDto) {
        OrderResponseDto dto = orderService.createOrder(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "주문이 성공적으로 생성되었습니다.",
                        dto
                ));
    }

    @GetMapping("/{orderId}")
    @Override
    public ResponseEntity<ResponseDto<OrderResponseDto>> getOrder(
            @PathVariable Long orderId) {
        OrderResponseDto dto = orderService.getOrder(orderId);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "주문을 성공적으로 조회했습니다.",
                dto
        ));
    }

    @GetMapping("/")
    @Override
    public ResponseEntity<ResponseDto<List<OrderResponseDto>>> getAllOrders() {
        List<OrderResponseDto> list = orderService.getAllOrders();
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "모든 주문을 성공적으로 조회했습니다.",
                list
        ));
    }

    @PutMapping("/{orderId}")
    @Override
    public ResponseEntity<ResponseDto<OrderResponseDto>> updateOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderRequestDto requestDto) {
        OrderResponseDto dto = orderService.updateOrder(orderId, requestDto);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "주문이 성공적으로 수정되었습니다.",
                dto
        ));
    }

    @DeleteMapping("/{orderId}")
    @Override
    public ResponseEntity<ResponseDto<Void>> deleteOrder(
            @PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "주문이 성공적으로 삭제되었습니다.",
                null
        ));
    }

    @GetMapping("/code/{orderCode}")
    @Override
    public ResponseEntity<ResponseDto<OrderResponseDto>> getOrderByCode(
            @PathVariable String orderCode) {
        OrderResponseDto dto = orderService.getOrderByCode(orderCode);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "주문 코드 조회 성공",
                dto));
    }

    @GetMapping("/user/{userId}")
    @Override
    public ResponseEntity<ResponseDto<List<OrderResponseDto>>> getOrdersByUser(
            @PathVariable Long userId) {
        List<OrderResponseDto> list = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "사용자별 주문 조회 성공",
                list));
    }

    @GetMapping("/{parentOrderId}/children")
    @Override
    public ResponseEntity<ResponseDto<List<OrderResponseDto>>> getChildOrders(
            @PathVariable Long parentOrderId) {
        List<OrderResponseDto> list = orderService.getChildOrders(parentOrderId);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "하위 주문 조회 성공",
                list));
    }
}
