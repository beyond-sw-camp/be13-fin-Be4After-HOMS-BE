package com.beyond.homs.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomListDto {
    private String roomId;
    private ParticipantDto user1; // 이제 ParticipantDto 객체
    private ParticipantDto user2; // 이제 ParticipantDto 객체
    private String lastMessageContent;
    private LocalDateTime lastMessageSentAt;
}

