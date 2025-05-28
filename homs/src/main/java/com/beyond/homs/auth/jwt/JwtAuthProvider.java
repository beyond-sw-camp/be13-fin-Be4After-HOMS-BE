package com.beyond.homs.auth.jwt;

import com.beyond.homs.common.jwt.JwtService;
import com.beyond.homs.user.data.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JwtAuthProvider {
    private final JwtService jwtService;
    private final RedisTemplate<String, String> redisTemplate;
    private static final long ACCESS_TOKEN_EXP = 1000L * 60L * 60L;     // 15분
//    private static final long ACCESS_TOKEN_EXP = 1000L * 60L;     // 1분

    private static final long REFRESH_TOKEN_EXP = 1000L * 60L * 60L * 24L;
//    private static final long REFRESH_TOKEN_EXP = 1000L * 60L * 2L; //

    @Value("${springboot.jwt.issuer}")
    private String issuer;

    public JwtAuthProvider(
            JwtService jwtService,
            RedisTemplate<String, String> redisTemplate) {
        this.jwtService = jwtService;
        this.redisTemplate = redisTemplate;
    }

    public String createAccessToken(String sub, UserRole role) {
        Map<String, String> claims = new HashMap<>();
        claims.put("sub", sub);
        claims.put("role", role.getValue());
        claims.put("iss", issuer);
        return jwtService.generateToken(claims, ACCESS_TOKEN_EXP);
    }

    public String createRefreshToken(String sub) {
        Map<String, String> claims = new HashMap<>();
        claims.put("sub", sub);
        claims.put("iss", issuer);
        String refreshToken = jwtService.generateToken(claims, REFRESH_TOKEN_EXP);
        redisTemplate.opsForValue().set(createRefreshKey(sub), refreshToken, REFRESH_TOKEN_EXP, TimeUnit.MILLISECONDS);
        return refreshToken;
    }

    private String createRefreshKey(String sub) {
        return "refresh:" + sub;
    }

    public String getRefreshToken(String sub) {
        return redisTemplate.opsForValue().get(createRefreshKey(sub));
    }

    public void addBlackList(String accessToken) {
        redisTemplate.opsForValue()
                .set(createBlackListKey(accessToken), "true", ACCESS_TOKEN_EXP, TimeUnit.MILLISECONDS);
    }

    public boolean isBlackList(String accessToken) {
        return redisTemplate.hasKey(createBlackListKey(accessToken));
    }

    private String createBlackListKey(String accessToken) {
        return "blacklist:" + accessToken;
    }

    public String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public boolean isValidIssuer(String accessToken) {
        return jwtService.getClaims(accessToken).get("iss").equals(issuer);
    }

    private boolean hasRole(String accessToken) {
        return jwtService.getClaims(accessToken).get("role") != null;
    }

    public boolean isValidAccessToken(String accessToken) {
        return  accessToken != null
                && jwtService.validateToken(accessToken)
                && !isBlackList(accessToken)
                && isValidIssuer(accessToken);
    }
}
