package com.beyond.homs.chat.interceptor;

import com.beyond.homs.common.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtService jwtService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            // 1) STOMP CONNECT 헤더에서 Authorization 가져오기
            String rawHeader = accessor.getFirstNativeHeader("Authorization");
            if (rawHeader == null || !rawHeader.startsWith("Bearer ")) {
                System.out.println("[StompHandler] Authorization 헤더 누락 또는 형식 오류: " + rawHeader);
                return null; // CONNECT 자체를 거부
            }

            String token = rawHeader.substring(7); // "Bearer " 이후 실제 토큰만 추출
            try {
                // 2) 토큰 유효성 검사
                if (!jwtService.validateToken(token)) {
                    System.out.println("[StompHandler] 유효하지 않은 JWT 토큰: " + token);
                    return null;
                }

                // 3) 토큰에서 userId 추출
                String userId = jwtService.getUserId(token);

                // 4) 인증 객체 생성하여 WebSocket 세션에 바인딩
                accessor.setUser(new UsernamePasswordAuthenticationToken(userId, null, null));
            } catch (Exception ex) {
                System.out.println("[StompHandler] JWT 검증 중 예외 발생: " + ex.getMessage());
                return null;
            }
        }

        return message;
    }
}

