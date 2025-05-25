package com.beyond.homs.order.dto;

import com.beyond.homs.order.data.ClaimEnum;
import com.beyond.homs.order.data.ClaimStatusEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ClaimRequestDto {
    @NotNull
    private final Long orderId;

    @NotNull
    private final Long productId;

    @NotNull
    private final ClaimEnum reason;

    @Size(max = 1024)
    private final String details;

    @NotNull
    private final ClaimStatusEnum status;
}
