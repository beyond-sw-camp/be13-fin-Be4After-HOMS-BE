package com.beyond.homs.order.dto;

import com.beyond.homs.order.data.ClaimStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimStatusUpdateDto {

    @NotNull
    private ClaimStatusEnum status;

}
