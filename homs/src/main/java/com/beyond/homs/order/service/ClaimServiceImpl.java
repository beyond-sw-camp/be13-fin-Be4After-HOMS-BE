package com.beyond.homs.order.service;

import com.beyond.homs.common.exception.exceptions.CustomException;
import com.beyond.homs.common.exception.messages.ExceptionMessage;
import com.beyond.homs.common.util.SecurityUtil;
import com.beyond.homs.order.data.ClaimSearchOption;
import com.beyond.homs.order.data.ClaimStatusEnum;
import com.beyond.homs.order.data.OrderSearchOption;
import com.beyond.homs.order.dto.ClaimListResponseDto;
import com.beyond.homs.order.dto.ClaimRequestDto;
import com.beyond.homs.order.dto.ClaimResponseDto;
import com.beyond.homs.order.entity.Claim;
import com.beyond.homs.order.entity.OrderItem;
import com.beyond.homs.order.entity.OrderItemId;
import com.beyond.homs.order.repository.ClaimRepository;
import com.beyond.homs.order.repository.OrderItemRepository;
import com.beyond.homs.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.beyond.homs.user.data.UserRole.ROLE_USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public ClaimResponseDto createClaim(ClaimRequestDto requestDto) {
        // 1) 복합키 생성
        OrderItemId itemId = OrderItemId.builder()
                .orderId(requestDto.getOrderId())
                .productId(requestDto.getProductId())
                .build();

        // 2) 연관된 OrderItem 조회
        OrderItem orderItem = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "OrderItem not found: orderId=" + requestDto.getOrderId() +
                                ", productId=" + requestDto.getProductId()));

        // 3) Claim 엔티티 생성
        Claim claim = Claim.builder()
                .orderItem(orderItem)
                .reason(requestDto.getReason())          // ClaimEnum
                .details(requestDto.getDetails())        // 상세 내용
                .status(requestDto.getStatus())          // ClaimStatusEnum
                .build();

        // 4) 저장
        Claim saved = claimRepository.save(claim);

        // 5) 응답 DTO 변환
        return toResponseDto(saved);
    }

    @Override
    public Page<ClaimResponseDto> getClaims(Long orderId, Long claimId, ClaimSearchOption option, String keyword, Pageable pageable){
        Page<ClaimResponseDto> searchResult = claimRepository.findClaim(orderId, claimId, option, keyword, pageable);

        // 검색결과가 없는 경우 예외 처리
        if (searchResult.isEmpty()) {
            throw new CustomException(ExceptionMessage.ORDER_NOT_FOUND);
        }

        return searchResult;
    }

    @Override
    public Page<ClaimListResponseDto> getAllClaimOrders(OrderSearchOption option, String keyword, Pageable pageable) {
        Long userId = null;

        // 로그인한 유저가 일반 유저라면 해당 유저의 Id값으로 검색
        if(SecurityUtil.getCurrentUserRole() == ROLE_USER){
            userId = SecurityUtil.getCurrentUserId();
        }

        Page<ClaimListResponseDto> searchResult = orderRepository.findClaimOrders(option, keyword, userId, pageable);
        List<ClaimListResponseDto> orderDtos = searchResult.getContent();

        // 검색결과가 없는 경우 예외 처리
        if (orderDtos.isEmpty()) {
            throw new CustomException(ExceptionMessage.ORDER_NOT_FOUND);
        }

        // 각 주문에 대한 클레임 해결 상태 판단 및 DTO에 설정
        List<ClaimListResponseDto> processedOrderDtos = orderDtos.stream()
                .peek(orderDto -> {
                    List<Claim> claimsForOrder = claimRepository.findByOrderItem_Order_orderId(orderDto.getOrderId());
                    boolean allClaimsResolved = !claimsForOrder.isEmpty() &&
                                                claimsForOrder.stream()
                                                    .allMatch(claim -> claim.getStatus().isResolved());
                    orderDto.setAllClaimsResolved(allClaimsResolved); // DTO에 결과 설정
                })
                .toList();

        return new PageImpl<>(processedOrderDtos, pageable, searchResult.getContent().size());
    }

    @Override
    @Transactional
    public void updateStatus(Long claimId, ClaimStatusEnum newStatus) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new EntityNotFoundException("Claim not found: " + claimId));
        claim.updateStatus(newStatus);
        // 영속성 컨텍스트가 관리하므로 별도 save() 호출 불필요
    }

    private ClaimResponseDto toResponseDto(Claim claim) {
        return new ClaimResponseDto(
                claim.getClaimId(),
                claim.getOrderItem().getOrder().getOrderCode(),
                claim.getOrderItem().getProduct().getProductName(),
                // 배송지 완성 아직 안되서 이렇게 타야됨.
                claim.getOrderItem().getOrder().getUser().getCompany().getCompanyName(),
                claim.getReason(),
                claim.getDetails(),
                claim.getOrderItem().getQuantity(),
                claim.getStatus()
        );
    }
}
