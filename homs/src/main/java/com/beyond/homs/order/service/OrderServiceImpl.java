package com.beyond.homs.order.service;

import com.beyond.homs.order.dto.OrderRequestDto;
import com.beyond.homs.order.dto.OrderResponseDto;
import com.beyond.homs.order.entity.Order;
import com.beyond.homs.order.repository.OrderRepository;
// import com.beyond.homs.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.beyond.homs.order.data.OrderStatusEnum.BEFORE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
//    private final UserRepository userRepository;
//    private final DeliveryAddressRepository addressRepository;
    private final OrderNumberGenerator orderNumberGenerator;

    @Override
    @Transactional
    public OrderResponseDto createOrder() {
        // 1) 사용자 조회
//        User user = userRepository.findById(requestDto.getUserId())
//                .orElseThrow(() -> new EntityNotFoundException(
//                        "해당 유저를 찾을 수 없습니다. userId=" + requestDto.getUserId()));
//        DeliveryAddress addr = addressRepository.findById(dto.getDeliveryAddressId())
//                .orElseThrow(() -> new EntityNotFoundException("Address not found: " + dto.getDeliveryAddressId()));

        // 2-1) 재귀
        // Order parent = null;
        // if (requestDto.getParentOrderId() != null) {
        //     parent = orderRepository.findById(requestDto.getParentOrderId())
        //             .orElseThrow(() -> new EntityNotFoundException(
        //                     "참조할 주문이 없습니다. orderId2=" + requestDto.getParentOrderId()));
        // }


        // 2-2) 엔티티 생성
        Order order = Order.builder()
                // .orderCode(requestDto.getOrderCode())
                // .dueDate(requestDto.getDueDate())
                // .approved(requestDto.isApproved())
                // .orderStatus(requestDto.getOrderStatus())
                .orderCode(orderNumberGenerator.generateOrderNumber())
                .dueDate(LocalDateTime.now()) // 현재 시간 대입 (임시)
                .approved(true) // 초기 상태는 true
                // .rejectReason(requestDto.getRejectReason()) // 빈값
                // .parentOrder(parent) // 빈값
                .orderStatus(BEFORE) // 고정값
                // .user(user)
//                .deliveryAddress(addr)
                .build();
//                .linkParent(parent);

        // 3) 저장
        Order saved = orderRepository.save(order);

        // 4) DTO 변환 후 반환
        return toResponseDto(saved);
    }

    @Override
    public OrderResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "해당 주문을 찾을 수 없습니다. orderId=" + orderId));
        return toResponseDto(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto requestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "해당 주문을 찾을 수 없습니다. orderId=" + orderId));

        // 주문 정보 업데이트
        order.updateOrderCode(requestDto.getOrderCode());
        order.updateDueDate(requestDto.getDueDate());
        order.updateOrderStatus(requestDto.getOrderStatus());

        // 승인 / 거절 분기 처리
        if (requestDto.isApproved()) {
            order.approve();  // 파라미터 없는 approve()
        } else {
            order.reject(requestDto.getRejectReason());  // 거절 사유는 필요
        }

        if(requestDto.getParentOrderId() != null) {
            Order parent = orderRepository.findById(requestDto.getParentOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent order not found: " + requestDto.getParentOrderId()));
            order.updateParentOrder(parent);
        } else {
            order.updateParentOrder(null);
        }

//        DeliveryAddress addr = addressRepository.findById(requestDto.getDeliveryAddressId())
//                .orElseThrow(() -> new EntityNotFoundException("Address not found: " + requestDto.getDeliveryAddressId()));
//        order.updateDeliveryAddress(addr);

        // flush & update
        Order updated = orderRepository.save(order);
        return toResponseDto(updated);
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException("해당 주문을 찾을 수 없습니다. orderId=" + orderId);
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    public OrderResponseDto getOrderByCode(String orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new EntityNotFoundException("Order not found by code: " + orderCode));
        return toResponseDto(order);
    }

    @Override
    public List<OrderResponseDto> getOrdersByUser(Long userId) {
        return orderRepository.findAllByUser_UserId(userId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDto> getChildOrders(Long parentOrderId) {
        return orderRepository.findAllByParentOrder_OrderId(parentOrderId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }


    private OrderResponseDto toResponseDto(Order order) {
        return new OrderResponseDto(
                order.getOrderId(),
                order.getOrderCode(),
//                order.getUser().getCompany().getCompanyName(),
//                order.getDeliveryAddress().getDeliveryName(),
                order.getOrderDate(),
                order.getDueDate(),
                order.isApproved(),
                order.getParentOrder() != null ? order.getParentOrder().getOrderId() : null,
                order.getRejectReason(),
                order.getOrderStatus()
        );
    }
}

