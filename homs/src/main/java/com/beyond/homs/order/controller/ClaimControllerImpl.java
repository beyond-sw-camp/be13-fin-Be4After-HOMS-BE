package com.beyond.homs.order.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.order.dto.ClaimRequestDto;
import com.beyond.homs.order.dto.ClaimResponseDto;
import com.beyond.homs.order.service.ClaimService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/claim")
public class ClaimControllerImpl implements ClaimController {
    private final ClaimService claimService;

    @PostMapping("/")
    @Override
    public ResponseEntity<ResponseDto<ClaimResponseDto>> createClaim(
            @Valid @RequestBody ClaimRequestDto requestDto) {
        ClaimResponseDto dto = claimService.createClaim(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "클레임이 성공적으로 신청되었습니다.",
                        dto
                ));
    }
}
