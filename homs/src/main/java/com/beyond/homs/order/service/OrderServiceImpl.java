package com.beyond.homs.order.service;

import com.beyond.homs.common.exception.exceptions.CustomException;
import com.beyond.homs.common.exception.messages.ExceptionMessage;
import com.beyond.homs.common.util.SecurityUtil;
import com.beyond.homs.company.repository.CompanyRepository;
import com.beyond.homs.order.data.OrderSearchOption;
import com.beyond.homs.order.dto.*;
import com.beyond.homs.order.entity.Order;
import com.beyond.homs.order.entity.OrderItem;
import com.beyond.homs.order.repository.OrderItemRepository;
import com.beyond.homs.order.repository.OrderRepository;
import com.beyond.homs.product.entity.Product;
import com.beyond.homs.product.repository.ProductRepository;
import com.beyond.homs.user.entity.User;
import com.beyond.homs.wms.entity.DeliveryAddress;
import com.beyond.homs.wms.repository.DeliveryAddRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.beyond.homs.order.data.OrderStatusEnum.BEFORE;
import static com.beyond.homs.user.data.UserRole.ROLE_USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
//    private final DeliveryAddressRepository addressRepository;
    private final OrderNumberGenerator orderNumberGenerator;
    private final CompanyRepository companyRepository;
    private final DeliveryAddRepository deliveryAddRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    @Override
    public OrderResponseDto createOrder() {
        // 1) 사용자 조회
        User currentUser = SecurityUtil.getCurrentUser();

        // 2-2) 엔티티 생성
        Order order = Order.builder()
                .orderCode(orderNumberGenerator.generateOrderNumber())
                .approved(false)
                .orderStatus(BEFORE) // 고정값
                .user(currentUser)
                .build();

        // 3-1) 저장
        Order saved = orderRepository.save(order);

        // 3-2) 연관관계가 Lazy로 설정되어 있기 때문에 company를 불러오기도 전에 종료되는 문제가 있음
        // 이를 해결하기 위해서 연관 관계에 속한 데이터를 미리 가져옴
        Order fullOrder = orderRepository.findByIdWithUserAndCompany(saved.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("생성된 주문을 찾을 수 없습니다. ID: " + saved.getOrderId()));

        // 4) DTO 변환 후 반환
        return toResponseDto(fullOrder);
    }

    @Override
    public OrderResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "해당 주문을 찾을 수 없습니다. orderId=" + orderId));
        return toResponseDto(order);
    }

    @Override
    public Page<OrderResponseDto> getAllOrders(OrderSearchOption option, String keyword, Pageable pageable) {
        Long userId = null;
        
        // 로그인한 유저가 일반 유저라면 해당 유저의 Id값으로 검색
        if(SecurityUtil.getCurrentUserRole() == ROLE_USER){
            userId = SecurityUtil.getCurrentUserId();
        }

        Page<OrderResponseDto> searchResult = orderRepository.findOrders(option, keyword, userId, pageable);

        // 검색결과가 없는 경우 예외 처리
        if (searchResult.isEmpty()) {
            throw new CustomException(ExceptionMessage.ORDER_NOT_FOUND);
        }

        return searchResult;
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

    @Transactional
    @Override
    public void setApprove(Long orderId, OrderApproveRequestDto requestDto){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "해당 주문을 찾을 수 없습니다. orderId=" + orderId));

        // 승인 / 거절 분기 처리
        if (requestDto.isApproved()) {
            order.updateApprove(true);  // 파라미터 없는 approve()
        } else {
            order.reject(requestDto.getRejectReason());  // 거절 사유는 필요
        }
        orderRepository.save(order);
    }

    @Transactional
    @Override
    public OrderResponseDto updateOrderDate(Long orderId, OrderDateRequestDto requestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "해당 주문을 찾을 수 없습니다. orderId=" + orderId));

        // 납품일 업데이트
        order.updateDueDate(requestDto.getDueDate());

       DeliveryAddress addr = deliveryAddRepository.findByAddressId(requestDto.getDeliveryAddressId());
       order.updateDeliveryAddress(addr);

        // flush & update
        Order updated = orderRepository.save(order);
        return toResponseDto(updated);
    }

    @Override
    public OrderResponseDto getOrderByCode(String orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new EntityNotFoundException("Order not found by code: " + orderCode));
        return toResponseDto(order);
    }

    @Override
    public List<OrderResponseDto> getChildOrders(Long parentOrderId) {
        return orderRepository.findAllByParentOrder_OrderId(parentOrderId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDeliveryResponseDTO> getDeliveryInfo() {
        List<Order> orders = orderRepository.findAll();
        return toDeliveryResponseDtoList(orders);
    }

    @Override
    public List<OrderDeliveryResponseDTO> getDeliveryInfoByUser(Long userId) {
        List<Order> orders = orderRepository.findAllByUser_UserId(userId);
        return toDeliveryResponseDtoList(orders);
    }

    @Override
    public void createChildOrder(OrderParentRequestDto requestDto){

        // 1) 사용자 조회
        User currentUser = SecurityUtil.getCurrentUser();

        Long productId = requestDto.getProduct().getProductId();

        // 현재 선택한 상품에 대한 정보를 저장
        Product product = productRepository.findById( productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " +  productId));

        // 자식 주문이 존재하고 승인되지 않았다면 (이미 클레임 주문이 있다면)
        if (!requestDto.isApproved()){
            Order parent = orderRepository.findById(requestDto.getParentOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("참조할 주문이 없습니다. orderId2=" + requestDto.getParentOrderId()));

            // 만약에 승인되지 않았다면 해당 자식 주문에 추가
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .order(parent)
                    .quantity(requestDto.getProduct().getQuantity())
                    .build();
            // 2-3) 저장
            OrderItem save = orderItemRepository.save(orderItem);
        }
        else{ // 이미 승인되어버린 자식 주문이라면 새롭게 주문 생성
            Order parent = orderRepository.findById(requestDto.getOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("참조할 주문이 없습니다. orderId2=" + requestDto.getOrderId()));

            // 2-2) 엔티티 생성
            Order order = Order.builder()
                    .orderCode(orderNumberGenerator.generateOrderNumber())
                    .approved(false)
                    .orderStatus(BEFORE) // 고정값
                    .user(currentUser)
                    .parentOrder(parent)
                    .build();

            // 3-1) 저장
            Order childOrder = orderRepository.save(order);

            // 만약에 승인되지 않았다면 해당 자식 주문에 추가
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .order(childOrder)
                    .quantity(requestDto.getProduct().getQuantity())
                    .build();
            // 2-3) 저장
            OrderItem save = orderItemRepository.save(orderItem);
        }
    }

    private OrderResponseDto toResponseDto(Order order) {
        // 1. 회사 이름 (companyName)을 안전하게 가져오기
        // user나 company가 null일 수 있으므로 안전하게 체이닝합니다.
        String companyName = null;
        if (order.getUser() != null && order.getUser().getCompany() != null) {
            companyName = order.getUser().getCompany().getCompanyName();
        }

        // 2. 배송지 이름 (deliveryAddressName)을 안전하게 가져오기
        // deliveryAddress가 null일 수 있으므로 반드시 체크해야 합니다.
        String deliveryAddressName = null;
        if (order.getDeliveryAddress() != null) {
            deliveryAddressName = order.getDeliveryAddress().getDeliveryName();
        }

        return new OrderResponseDto(
            order.getOrderId(),
            order.getOrderCode(),
            companyName,
            deliveryAddressName,
            order.getOrderDate(),
            order.getDueDate(),
            order.isApproved(),
            order.getParentOrder() != null ? order.getParentOrder().getOrderId() : null,
            order.getRejectReason(),
            order.getOrderStatus()
        );
    }

    private List<OrderDeliveryResponseDTO> toDeliveryResponseDtoList(List<Order> orders) {
        return orders.stream()
                .map(order -> OrderDeliveryResponseDTO.builder()
                        .orderId(order.getOrderId())
                        .orderCode(order.getOrderCode())
                        .companyName(order.getUser().getCompany().getCompanyName())
                        .deliveryName(order.getDeliveryAddress() != null ? order.getDeliveryAddress().getDeliveryName() : null)
                        .orderDate(order.getOrderDate())
                        .deliveryDate(order.getDueDate())
                        .deliveryStatus(order.getOrderStatus() != null ? order.getOrderStatus().name() : null)
                        .build())
                .collect(Collectors.toList());
    }

}

