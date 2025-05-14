package com.beyond.homs.auth.controller;

import com.beyond.homs.auth.dto.SignInRequestDto;
import com.beyond.homs.auth.dto.TokenResponseDto;
import com.beyond.homs.common.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthController {
    ResponseEntity<ResponseDto<TokenResponseDto>> signIn(SignInRequestDto signInRequestDto);

    ResponseEntity<ResponseDto<Void>> signOut(String bearerToken);

    ResponseEntity<ResponseDto<TokenResponseDto>> refreshTokens(String bearerToken);
}
