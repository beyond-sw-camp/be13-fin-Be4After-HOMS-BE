package com.beyond.homs.order.repository;

import com.beyond.homs.order.data.ClaimSearchOption;
import com.beyond.homs.order.dto.ClaimResponseDto;
import com.beyond.homs.order.entity.Claim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClaimRepositoryCustom {
    Page<ClaimResponseDto> findClaim(Long orderId, ClaimSearchOption option, String keyword, Pageable pageable);
}
