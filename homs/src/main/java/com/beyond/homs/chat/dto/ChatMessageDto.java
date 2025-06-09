package com.beyond.homs.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * WebSocket 메시지 페이로드 및 REST 응답에 모두 사용
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private Long messageId;
    private String roomId;
    private Long senderId;
    private String content;
    private LocalDateTime sentAt;
}
