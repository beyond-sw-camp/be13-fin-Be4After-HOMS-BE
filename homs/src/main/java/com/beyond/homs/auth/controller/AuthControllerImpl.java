package com.beyond.homs.auth.controller;

import com.beyond.homs.auth.dto.SignInRequestDto;
import com.beyond.homs.auth.dto.TokenResponseDto;
import com.beyond.homs.auth.service.AuthService;
import com.beyond.homs.common.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    @PostMapping("/signin")
    public ResponseEntity<ResponseDto<TokenResponseDto>> signIn(@RequestBody @Valid SignInRequestDto signInRequestDto) {
        TokenResponseDto tokenResponseDto = authService.login(signInRequestDto.userName(), signInRequestDto.password());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto<>(
                                HttpStatus.CREATED.value(),
                                "Login success",
                                tokenResponseDto
                        ));
    }

    @Override
    @PostMapping("/signout")
    public ResponseEntity<ResponseDto<Void>> signOut(@RequestHeader("Authorization") String bearerToken) {
        authService.logout(bearerToken);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto<>(
                                HttpStatus.CREATED.value(),
                                "Logout success",
                                null
                        ));
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto<TokenResponseDto>> refreshTokens(@RequestHeader("Authorization") String bearerToken) {
        TokenResponseDto tokenResponseDto = authService.refresh(bearerToken);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto<>(
                                HttpStatus.CREATED.value(),
                                "Token refresh success",
                                tokenResponseDto
                        ));
    }
}
