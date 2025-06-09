package com.beyond.homs.order.data;

public enum ClaimStatusEnum {
    EXCHANGE, CANCEL, COMPLETE;

    public boolean isResolved(){
        return this != EXCHANGE;
    }
}
