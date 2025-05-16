package com.beyond.homs.auth.service;

import com.beyond.homs.auth.dto.TokenResponseDto;
import com.beyond.homs.auth.jwt.JwtAuthProvider;
import com.beyond.homs.common.jwt.JwtService;
import com.beyond.homs.user.entity.User;
import com.beyond.homs.user.entity.UserLogin;
import com.beyond.homs.user.repository.UserLoginRepository;
import com.beyond.homs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserLoginRepository userLoginRepository;

    private final JwtAuthProvider jwtProvider;

    private final JwtService jwtService;

    @Override
    public TokenResponseDto login(String username, String password) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserLogin userLogin = userLoginRepository.findById(user.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, userLogin.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        return new TokenResponseDto(
                jwtProvider.createAccessToken(user.getUserId().toString(), user.getRole()),
                jwtProvider.createRefreshToken(user.getUserId().toString())
        );
    }

    @Override
    public void logout(String bearerToken) {
        if (bearerToken != null) {
            String accessToken = jwtProvider.resolveToken(bearerToken);
            if (!jwtService.validateToken(accessToken)) {
                throw new RuntimeException("Invalid token");
            }
            jwtProvider.addBlackList(accessToken);
        } else {
            throw new RuntimeException("Invalid token");
        }
    }

    @Override
    public TokenResponseDto refresh(String bearerToken) {
        if (bearerToken != null) {
            String resolvedToken = jwtProvider.resolveToken(bearerToken);
            if (!jwtService.validateToken(resolvedToken)) {
                throw new RuntimeException("Invalid token");
            }
            String subject = jwtService.getClaims(resolvedToken).getSubject();
            String savedRefreshToken = jwtProvider.getRefreshToken(subject);
            // user의 refreshToken과 redis에 저장된 refreshToken 비교
            if (!resolvedToken.equals(savedRefreshToken)) {
                throw new RuntimeException("Invalid refresh token");
            }
            jwtProvider.addBlackList(resolvedToken);

            User user = userRepository.findById(Long.valueOf(subject))
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return new TokenResponseDto(
                    jwtProvider.createAccessToken(user.getUserId().toString(), user.getRole()),
                    jwtProvider.createRefreshToken(user.getUserId().toString())
            );
        }
        throw new RuntimeException("Invalid token");
    }
}
