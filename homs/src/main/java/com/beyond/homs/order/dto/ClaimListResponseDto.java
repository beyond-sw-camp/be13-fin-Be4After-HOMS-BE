package com.beyond.homs.order.dto;

import com.beyond.homs.order.data.OrderStatusEnum;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor // Lombok에게 기본 생성자(인자 없는)를 만들라고 지시
public class ClaimListResponseDto {
    private Long orderId;

    private String orderCode;

    private String companyName;

//    private String deliveryName;

    private Date orderDate;

    private Date dueDate;

    private boolean approved;

    private Long parentOrderId;

    private String rejectReason;

    private OrderStatusEnum orderStatus;

    private boolean isAllClaimsRejected;

    @QueryProjection //
    public ClaimListResponseDto(
            Long orderId,
            String orderCode,
            String companyName,
            Date orderDate,
            Date dueDate,
            boolean approved, // QueryDSL 에러 메시지가 Boolean이면 여기도 Boolean으로 변경 고려
            Long parentOrderId,
            String rejectReason,
            OrderStatusEnum orderStatus
    ) {
        this.orderId = orderId;
        this.orderCode = orderCode;
        this.companyName = companyName;
        this.orderDate = orderDate;
        this.dueDate = dueDate;
        this.approved = approved;
        this.parentOrderId = parentOrderId;
        this.rejectReason = rejectReason;
        this.orderStatus = orderStatus;
        this.isAllClaimsRejected = false; // 기본값 설정
    }

    public void setAllClaimsResolved(boolean allClaimsResolved) {
        this.isAllClaimsRejected = allClaimsResolved;
    }
}
