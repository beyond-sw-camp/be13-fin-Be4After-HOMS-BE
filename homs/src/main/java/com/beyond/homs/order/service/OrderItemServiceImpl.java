package com.beyond.homs.order.service;

import com.beyond.homs.common.exception.exceptions.CustomException;
import com.beyond.homs.common.exception.messages.ExceptionMessage;
import com.beyond.homs.order.dto.OrderItemRequestDto;
import com.beyond.homs.order.dto.OrderItemResponseDto;
import com.beyond.homs.order.dto.OrderItemSearchResponseDto;
import com.beyond.homs.order.dto.OrderResponseDto;
import com.beyond.homs.order.entity.Order;
import com.beyond.homs.order.entity.OrderItem;
import com.beyond.homs.order.entity.OrderItemId;
import com.beyond.homs.order.repository.OrderItemRepository;
import com.beyond.homs.order.repository.OrderRepository;
import com.beyond.homs.product.data.ProductSearchOption;
import com.beyond.homs.product.dto.ProductListDto;
import com.beyond.homs.product.entity.Product;
import com.beyond.homs.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public List<OrderItemResponseDto> addOrderItem(Long orderId, List<OrderItemRequestDto> requestDtos) {

        // 1) 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order not found: " + orderId));

        // 2) 각 OrderItemRequestDto에 대해 처리
        List<OrderItemResponseDto> responseDtos = new ArrayList<>();
    
        // 2-1) 상품 조회
        for (OrderItemRequestDto requestDto : requestDtos){
            Product product = productRepository.findById(requestDto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Product not found: " + requestDto.getProductId()));
            
            // 2-2) OrderItem 생성 (EmbeddedId는 엔티티 내부에서 자동 생성)
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .order(order)
                    .quantity(requestDto.getQuantity())
                    .build();

            // 2-3) 저장
            OrderItem saved = orderItemRepository.save(orderItem);

            // 2-4) DTO 변환 후 반환
            responseDtos.add(toResponseDto(saved));
        }
        
        // 3) 응답 DTO 반환
        return responseDtos;
    }

    @Override
    public Page<OrderItemSearchResponseDto> getOrderItems(Long orderId, ProductSearchOption option, String keyword, Pageable pageable) {
        Page<OrderItemSearchResponseDto> searchResult = orderItemRepository.findOrderItems(orderId, option, keyword, pageable);

        return searchResult;
    }

    @Transactional
    @Override
    public void deleteOrderItem(Long orderId, List<Long> productIds) {
        for (Long productId : productIds) {
            // 복합키를 직접 생성
            OrderItemId key = OrderItemId.builder()
                    .orderId(orderId)
                    .productId(productId)
                    .build();
            orderItemRepository.deleteById(key);
        }
    }

    @Override
    @Transactional
    public OrderItemResponseDto updateOrderItem(Long orderId, Long productId, Long quantity) {
        OrderItemId key = OrderItemId.builder()
                .orderId(orderId)
                .productId(productId)
                .build();

        OrderItem existing = orderItemRepository.findById(key)
                .orElseThrow(() -> new EntityNotFoundException(
                        "OrderItem not found: orderId=" + orderId + ", productId=" + productId));

        existing.changeQuantity(quantity);
        // Dirty Checking 으로 자동 반영됨
        return toResponseDto(existing);
    }

    @Override
    public List<OrderItemResponseDto> getAllItems() {
        List<OrderItem> orderItemList = orderItemRepository.findAll();

        List<OrderItemResponseDto> dtoList = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            dtoList.add(toResponseDto(orderItem));
        }

        return dtoList;
    }

    private OrderItemResponseDto toResponseDto(OrderItem orderItem) {

        Product product = orderItem.getProduct();
        Order order = orderItem.getOrder();

        Long parentOrderId = null;
        if (order.getParentOrder() != null) {
            parentOrderId = order.getParentOrder().getOrderId();
        }

        ProductListDto productListDto = new ProductListDto(
                product.getProductId(),
                product.getProductName(),
                product.getProductMinQuantity(),
                orderItem.getQuantity(),
                product.getCategory() != null ? product.getCategory() : null
        );

        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .orderCode(order.getOrderCode())
                .orderDate(order.getOrderDate())
                .dueDate(order.getDueDate())
                .approved(order.isApproved())
                .parentOrderId(parentOrderId)
                .rejectReason(order.getRejectReason())
                .orderStatus(order.getOrderStatus())
                .build();

        return new OrderItemResponseDto(
                orderResponseDto,
                productListDto,
                orderItem.getQuantity()
        );
    }
}

