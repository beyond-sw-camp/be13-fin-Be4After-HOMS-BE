package com.beyond.homs.order.service;

import com.beyond.homs.common.exception.exceptions.CustomException;
import com.beyond.homs.common.exception.messages.ExceptionMessage;
import com.beyond.homs.common.util.SecurityUtil;
import com.beyond.homs.order.data.ClaimSearchOption;
import com.beyond.homs.order.data.ClaimStatusEnum;
import com.beyond.homs.order.dto.ClaimRequestDto;
import com.beyond.homs.order.dto.ClaimResponseDto;
import com.beyond.homs.order.entity.Claim;
import com.beyond.homs.order.entity.OrderItem;
import com.beyond.homs.order.entity.OrderItemId;
import com.beyond.homs.order.repository.ClaimRepository;
import com.beyond.homs.order.repository.OrderItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.beyond.homs.user.data.UserRole.ROLE_USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
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

    // @Override
    // public List<ClaimResponseDto> getClaims(Long orderId) {
    //     return claimRepository.findAllByOrderItem_OrderItemId_OrderId(orderId).stream()
    //             .map(this::toResponseDto)
    //             .collect(Collectors.toList());
    // }

    @Override
    public Page<ClaimResponseDto> getClaims(Long orderId, ClaimSearchOption option, String keyword, Pageable pageable){
        Page<ClaimResponseDto> searchResult = claimRepository.findClaim(orderId, option, keyword, pageable);

        // 검색결과가 없는 경우 예외 처리
        if (searchResult.isEmpty()) {
            throw new CustomException(ExceptionMessage.ORDER_NOT_FOUND);
        }

        return searchResult;
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
