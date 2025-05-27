package com.beyond.homs.auth.service;

import com.beyond.homs.auth.dto.TokenResponseDto;

public interface AuthService {
    TokenResponseDto login(String username, String password);

    void logout(String bearerToken);

    TokenResponseDto refresh(String bearerToken);
}
