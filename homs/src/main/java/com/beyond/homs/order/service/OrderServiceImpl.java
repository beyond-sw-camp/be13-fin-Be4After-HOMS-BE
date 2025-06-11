package com.beyond.homs.order.service;

import com.beyond.homs.common.exception.exceptions.CustomException;
import com.beyond.homs.common.exception.messages.ExceptionMessage;
import com.beyond.homs.common.util.SecurityUtil;
import com.beyond.homs.company.repository.CompanyRepository;
import com.beyond.homs.order.data.OrderSearchOption;

import com.beyond.homs.order.data.OrderStatusEnum;

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
        boolean isAdmin = false;
        
        // 로그인한 유저가 일반 유저라면 해당 유저의 Id값으로 검색
        if(SecurityUtil.getCurrentUserRole() == ROLE_USER){
            userId = SecurityUtil.getCurrentUserId();
        }else{ // 아니면 관리자로 필터링
            isAdmin = true;
        }

        return orderRepository.findOrders(option, keyword, userId, isAdmin, pageable);
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

    @Transactional
    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatusEnum orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "해당 주문을 찾을 수 없습니다. orderId=" + orderId));

        // 배송 상태 업데이트
        order.updateOrderStatus(orderStatus);

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

    @Transactional
    @Override
    public Long createChildOrder(OrderParentRequestDto requestDto){

        // 1) 사용자 조회
        User currentUser = SecurityUtil.getCurrentUser();

        // 2) 현재 주문에 대한 정보
        Order currentOrder = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("참조할 주문이 없습니다. orderId=" + requestDto.getOrderId()));

        // 3) 승인되지 않고 배송전 상태인 자식 order 리스트 조회
        List<Order> childOrders = orderRepository.findOrdersByParentOrder_OrderIdAndApprovedAndOrderStatusAndRejectReasonIsNotNull(currentOrder.getOrderId(),false,BEFORE);
        Order childOrder = childOrders.stream()
                .findFirst() // 리스트의 첫 번째 요소를 Optional<Order>로 반환 (없으면 Optional.empty())
                .orElse(null); // Optional이 비어있으면 null을 반환

        // 자식 주문이 존재하고 승인되지 않았다면 (이미 클레임 주문이 있다면)
        if (childOrder != null) {
            // 기존 자식 주문에 모든 상품을 추가
            for (OrderItemRequestDto productItem : requestDto.getProduct()) { // requestDto.getProduct() -> requestDto.getProducts()로 변경
                Product product = productRepository.findById(productItem.getProductId()) // productItem에서 ID 가져오기
                        .orElseThrow(() -> new EntityNotFoundException("Product not found: " + productItem.getProductId()));

                OrderItem orderItem = OrderItem.builder()
                        .product(product)
                        .order(childOrder)
                        .quantity(productItem.getQuantity()) // productItem에서 quantity 가져오기
                        .build();
                orderItemRepository.save(orderItem);
                System.out.println("기존 자식 주문에 OrderItem 추가: " + orderItem.getOrder().getOrderId());
                return orderItem.getOrder().getOrderId();
            }

        } else{ // 이미 승인되어버린 자식 주문이라면 새롭게 주문 생성
            // 2-2) 엔티티 생성
            Order newChildOrder = Order.builder()
                    .orderCode(orderNumberGenerator.generateOrderNumber())
                    .approved(false)
                    .orderStatus(BEFORE) // 고정값
                    .user(currentUser)
                    .parentOrder(currentOrder)
                    .build();

            // 3-1) 저장
            newChildOrder = orderRepository.save(newChildOrder);

            // 새로 생성된 자식 주문에 모든 상품을 추가
            for (OrderItemRequestDto productItem : requestDto.getProduct()) { // requestDto.getProduct() -> requestDto.getProducts()로 변경
                Product product = productRepository.findById(productItem.getProductId()) // productItem에서 ID 가져오기
                        .orElseThrow(() -> new EntityNotFoundException("Product not found: " + productItem.getProductId()));

                OrderItem orderItem = OrderItem.builder()
                        .product(product)
                        .order(newChildOrder) // 새로 생성된 주문에 연결
                        .quantity(productItem.getQuantity()) // productItem에서 quantity 가져오기
                        .build();
                orderItemRepository.save(orderItem);
                System.out.println("새 자식 주문에 OrderItem 추가: " + orderItem.getOrder().getOrderId());
                return orderItem.getOrder().getOrderId();
            }
        }
        return null;
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

