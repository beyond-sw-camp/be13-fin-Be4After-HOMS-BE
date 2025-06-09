package com.beyond.homs.order.repository;

import com.beyond.homs.order.data.ClaimSearchOption;
import com.beyond.homs.order.dto.ClaimResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClaimRepositoryCustom {
    Page<ClaimResponseDto> findClaim(Long orderId, Long claimId, ClaimSearchOption option, String keyword, Pageable pageable);
}
