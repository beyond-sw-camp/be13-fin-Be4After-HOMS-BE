package com.beyond.homs.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtService {
    private final SecretKey secretKey;

    public JwtService(@Value("${springboot.jwt.secret}") String secret) {
        this.secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    public String generateToken(Map<String, String> claims, long expiration) {
        return Jwts.builder()
                .header().add("typ", "JWT").and() // typ 헤더 추가
                .claims(claims) // 공개 클레임
                .id(Long.toHexString(System.nanoTime())) // jti(JWT ID) 클레임
                .issuedAt(new Date()) // 발급 시간 설정
                .expiration(new Date(System.currentTimeMillis() + expiration)) // 만료 시간 설정
                .signWith(secretKey) // 서명을 생성
                .compact(); // JWT 토큰을 생성
    }

    public boolean validateToken(String token) {
        return !getClaims(token).getExpiration().before(new Date());
    }

    public Claims getClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getJti(String token) {
        return getClaims(token).getId();
    }
}
