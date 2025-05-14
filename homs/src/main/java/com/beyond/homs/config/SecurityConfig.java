package com.beyond.homs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/api-docs/**",          // Swagger 관련 경로 허용
                                "/swagger-ui/**",        // Swagger UI 경로 허용
                                "/v3/api-docs/**",       // OpenAPI 문서 경로 허용
                                "/swagger-resources/**"  // Swagger 리소스 허용
                        ).permitAll()               // 위 경로는 모두 허용
                        .anyRequest().permitAll()  // 나머지 요청도 모두 허용 (개발 단계에서)
                )
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (테스트 환경에서만 사용)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
