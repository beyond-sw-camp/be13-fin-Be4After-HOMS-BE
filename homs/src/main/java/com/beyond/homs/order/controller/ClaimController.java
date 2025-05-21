package com.beyond.homs.order.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.order.dto.ClaimRequestDto;
import com.beyond.homs.order.dto.ClaimResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "클레임 API", description = "클레임 API 목록")
public interface ClaimController {

    @Operation(summary = "클레임 생성", description = "클레임을 생성합니다.")
    ResponseEntity<ResponseDto<ClaimResponseDto>> createClaim(
            @Valid @RequestBody ClaimRequestDto claimRequestDto);
}
