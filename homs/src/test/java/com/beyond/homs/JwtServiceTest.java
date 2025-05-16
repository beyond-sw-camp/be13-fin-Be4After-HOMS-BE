package com.beyond.homs;

import com.beyond.homs.common.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class JwtServiceTest {
    @Autowired
    private JwtService jwtService;

    @Test
    public void createJwtTokenTest() {
        Map<String, String> claims = new HashMap<>();
        claims.put("sub", "asdf");
        claims.put("name", "Adrian");
        String jwtToken = jwtService.generateToken(claims, 3600);

        assertThat(jwtToken).isNotNull();
        assertThat(jwtService.validateToken(jwtToken)).isTrue();
        assertThat(jwtService.getClaims(jwtToken).get("name")).isEqualTo("Adrian");
    }
}
