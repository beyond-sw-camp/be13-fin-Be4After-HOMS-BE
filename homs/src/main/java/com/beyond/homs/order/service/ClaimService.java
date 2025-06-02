package com.beyond.homs.order.service;

import com.beyond.homs.order.data.ClaimSearchOption;
import com.beyond.homs.order.data.ClaimStatusEnum;
import com.beyond.homs.order.dto.ClaimRequestDto;
import com.beyond.homs.order.dto.ClaimResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ClaimService {
    ClaimResponseDto createClaim(@Valid ClaimRequestDto claimRequestDto);

    Page<ClaimResponseDto> getClaims(Long orderId, ClaimSearchOption option, String keyword, Pageable pageable);

    void updateStatus(Long claimId, ClaimStatusEnum newStatus);
}
