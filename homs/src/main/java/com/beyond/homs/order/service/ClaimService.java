package com.beyond.homs.order.service;

import com.beyond.homs.order.data.ClaimStatusEnum;
import com.beyond.homs.order.dto.ClaimRequestDto;
import com.beyond.homs.order.dto.ClaimResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClaimService {
    ClaimResponseDto createClaim(@Valid ClaimRequestDto claimRequestDto);

    List<ClaimResponseDto> getClaims(Long orderId);

    void updateStatus(Long claimId, ClaimStatusEnum newStatus);
}
