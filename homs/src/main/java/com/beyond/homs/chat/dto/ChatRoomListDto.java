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
    private Long user1Id;
    private Long user2Id;
    private String lastMessageContent;
    private LocalDateTime lastMessageSentAt;
}
