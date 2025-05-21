package com.beyond.homs.order.service;

import com.beyond.homs.order.dto.ClaimRequestDto;
import com.beyond.homs.order.dto.ClaimResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface ClaimService {
    ClaimResponseDto createClaim(@Valid ClaimRequestDto claimRequestDto);
}
