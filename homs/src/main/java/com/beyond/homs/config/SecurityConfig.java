package com.beyond.homs.config;

import com.beyond.homs.auth.jwt.JwtAuthFilter;
import com.beyond.homs.auth.jwt.JwtAuthProvider;
import com.beyond.homs.common.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthProvider jwtAuthProvider;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/api-docs/**",          // Swagger 관련 경로 허용
                                "/swagger-ui/**",        // Swagger UI 경로 허용
                                "/v3/api-docs/**",       // OpenAPI 문서 경로 허용
                                "/swagger-resources/**"  // Swagger 리소스 허용
                        ).permitAll()               // 위 경로는 모두 허용
                                .requestMatchers("/api/v1/auth/signin", "/api/v1/auth/refresh", "/api/v1/admin/user", "/auth/signout").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()  // 나머지 요청도 모두 허용 (개발 단계에서)
//                                .anyRequest().permitAll() // 개발 이후 삭제
                )
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화 (테스트 환경에서만 사용)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 적용
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // X-Frame-Options SAMEORIGIN 설정
                        .cacheControl(cache -> cache.disable()) // Cache-Control 헤더 비활성화
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(
                        new JwtAuthFilter(jwtAuthProvider, jwtService, userDetailsService), // JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(java.util.List.of("http://localhost:5173")); // 모든 Origin 허용 (개발 단계에서만 사용)
        configuration.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메서드
        configuration.setAllowedHeaders(java.util.List.of("*")); // 모든 헤더 허용
        configuration.setExposedHeaders(java.util.List.of("Authorization", "Link", "X-Total-Count")); // 클라이언트가 접근 가능한 헤더 추가 (필요시)
        configuration.setAllowCredentials(true); // 인증 정보 포함 여부 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
