package com.beyond.homs.auth.controller;

import com.beyond.homs.auth.dto.SignInRequestDto;
import com.beyond.homs.auth.dto.TokenResponseDto;
import com.beyond.homs.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "인증 API", description = "인증 API 목록")
public interface AuthController {
    @Operation(summary = "로그인", description = "로그인을 요청합니다.")
    ResponseEntity<ResponseDto<TokenResponseDto>> signIn(SignInRequestDto signInRequestDto);

    @Operation(summary = "로그아웃", description = "로그아웃을 요청합니다.")
    ResponseEntity<ResponseDto<Void>> signOut(String bearerToken);

    @Operation(summary = "토큰 레프레시", description = "인증 토큰을 재발급 합니다.")
    ResponseEntity<ResponseDto<TokenResponseDto>> refreshTokens(String bearerToken);
}
