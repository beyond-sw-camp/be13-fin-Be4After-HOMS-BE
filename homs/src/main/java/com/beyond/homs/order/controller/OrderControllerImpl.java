package com.beyond.homs.order.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.order.data.OrderSearchOption;
import com.beyond.homs.order.dto.*;
import com.beyond.homs.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderControllerImpl implements OrderController {
    private final OrderService orderService;

    @PostMapping("/")
    @Override
    public ResponseEntity<ResponseDto<OrderResponseDto>> createOrder(){
        OrderResponseDto dto = orderService.createOrder();
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
    public ResponseEntity<ResponseDto<Page<OrderResponseDto>>> getAllOrders(
            @RequestParam(required = false) OrderSearchOption option,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {

        Page<OrderResponseDto> list = orderService.getAllOrders(option,keyword,pageable);
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

    @PutMapping("/{orderId}/approve")
    @Override
    public ResponseEntity<ResponseDto<Void>> setApprove(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderApproveRequestDto requestDto) {
        orderService.setApprove(orderId,requestDto);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "성공적으로 수정되었습니다.",
                null
        ));
    }

    @PutMapping("/{orderId}/date")
    @Override
    public ResponseEntity<ResponseDto<Void>> updateDate(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderDateRequestDto requestDto) {
        orderService.updateOrderDate(orderId,requestDto);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "성공적으로 수정되었습니다.",
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

    @GetMapping("/deliveryInfo")
    @Override
    public ResponseEntity<ResponseDto<List<OrderDeliveryResponseDTO>>>getDeliveryInfo(){
        List<OrderDeliveryResponseDTO> deliveryInfo = orderService.getDeliveryInfo();
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "배송 정보 조회 성공",
                deliveryInfo
        ));
    }

     @GetMapping("/deliveryInfo/{userId}")
     @Override
     public ResponseEntity<ResponseDto<List<OrderDeliveryResponseDTO>>> getDeliveryInfoByUser(
             @PathVariable Long userId) {
         List<OrderDeliveryResponseDTO> list = orderService.getDeliveryInfoByUser(userId);
         return ResponseEntity.ok(new ResponseDto<>(
                 HttpStatus.OK.value(),
                 "사용자별 주문 조회 성공",
                 list));
     }

    @PostMapping("/child")
    @Override
    public ResponseEntity<ResponseDto<Long>> getDeliveryInfoByUser(
            @RequestBody OrderParentRequestDto requestDto) {
        Long childOrder = orderService.createChildOrder(requestDto);
        return ResponseEntity.ok(new ResponseDto<>(
                HttpStatus.OK.value(),
                "하위 주문 생성 성공",
                childOrder));
    }
}
