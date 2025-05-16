package com.beyond.homs;

import com.beyond.homs.auth.jwt.JwtAuthProvider;
import com.beyond.homs.common.jwt.JwtService;
import com.beyond.homs.user.data.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class JwtProviderTest {
    @Autowired
    private JwtAuthProvider jwtProvider;

    @Autowired
    private JwtService jwtService;

    @Test
    public void createAccessTokenTest() {
        String accessToken = jwtProvider.createAccessToken("testUser", UserRole.ROLE_USER);
        assertThat(accessToken).isNotNull();
        assertThat(jwtService.getClaims(accessToken).get("sub")).isEqualTo("testUser");
    }

    @Test
    public void createRefreshTokenTest() {
        String refreshToken = jwtProvider.createRefreshToken("testUser");
        assertThat(refreshToken).isNotNull();
        assertThat(jwtService.getClaims(refreshToken).get("sub")).isEqualTo("testUser");
    }

    @Test
    public void addBlackListTest() {
        String accessToken = jwtProvider.createAccessToken("testUser", UserRole.ROLE_USER);
        jwtProvider.addBlackList(accessToken);
        assertThat(jwtProvider.isBlackList(accessToken)).isTrue();
    }
}
