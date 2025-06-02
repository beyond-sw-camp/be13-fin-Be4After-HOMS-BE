package com.beyond.homs.config;

import com.beyond.homs.chat.interceptor.StompHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 메시지 브로커 구성
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub"); // 구독 접두사(prefix)
        config.setApplicationDestinationPrefixes("/pub"); // 메시지 발행 접두사(prefix)
    }

    // JWT 인증 필터 삽입을 위한 설정
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new StompHandler()); // JWT 인증 핸들러
    }

    // WebSocket 연결 endpoint 정의
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp") // 클라이언트 접속 주소
                .setAllowedOriginPatterns("*") // CORS 허용
                .withSockJS(); // SockJS fallback
    }
}
