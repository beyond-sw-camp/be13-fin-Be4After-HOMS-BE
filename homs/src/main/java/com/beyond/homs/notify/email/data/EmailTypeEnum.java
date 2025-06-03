package com.beyond.homs.notify.email.data;

public enum EmailTypeEnum {
    // 주문 관련
    ORDER_CONFIRMATION, // 주문 승인 (주문 완료, 주문 확인)
    ORDER_CANCELLATION, // 주문 거절 (사유 포함)
    SHIPPING_UPDATE,    // 배송 상태 알림 (배송 시작, 배송 중, 배송 완료 등)

    // 계정 관련
    ACCOUNT_CREATED, // 계정 생성 알림

    // 정산 관련
    SETTLE_STATUS
}
