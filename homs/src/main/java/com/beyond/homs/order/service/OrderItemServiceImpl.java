package com.beyond.homs.order.service;

import com.beyond.homs.order.dto.OrderItemRequestDto;
import com.beyond.homs.order.dto.OrderItemResponseDto;
import com.beyond.homs.order.dto.OrderResponseDto;
import com.beyond.homs.order.entity.Order;
import com.beyond.homs.order.entity.OrderItem;
import com.beyond.homs.order.entity.OrderItemId;
import com.beyond.homs.order.repository.OrderItemRepository;
import com.beyond.homs.order.repository.OrderRepository;
import com.beyond.homs.product.dto.ProductListDto;
import com.beyond.homs.product.entity.Product;
import com.beyond.homs.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderItemResponseDto addOrderItem(Long orderId, OrderItemRequestDto requestDto) {

        // 1) 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order not found: " + orderId));

        // 2) 상품 조회
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Product not found: " + requestDto.getProductId()));

        // 3) OrderItem 생성 (EmbeddedId는 엔티티 내부에서 자동 생성)
        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .order(order)
                .quantity(requestDto.getQuantity())
                .build();

        // 4) 저장
        OrderItem saved = orderItemRepository.save(orderItem);

        // 5) DTO 변환 후 반환
        return toResponseDto(saved);
    }

    @Override
    public List<OrderItemResponseDto> getOrderItems(Long orderId) {
        return orderItemRepository.findAllByOrder_OrderId(orderId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteOrderItem(Long orderId, Long productId) {
        // 복합키를 직접 생성
        OrderItemId key = OrderItemId.builder()
                .orderId(orderId)
                .productId(productId)
                .build();
        orderItemRepository.deleteById(key);
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
                product.getCategory()
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
                productListDto
        );
    }
}

