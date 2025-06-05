package com.beyond.homs.chat.service;

import com.beyond.homs.chat.entity.ChatMessage;
import com.beyond.homs.chat.entity.ChatRoom;
import java.util.List;

public interface ChatService {
    ChatRoom getOrCreateRoom(Long userId1, Long userId2);
    ChatMessage saveMessage(String roomId, Long senderId, String content);
    List<ChatMessage> findAllMessages(String roomId);
    List<ChatMessage> findRecentMessages(String roomId, int size);
    ChatMessage findLastMessageByRoomId(String roomId);

    // 새로운 메서드: 사용자 정보(managerName, companyName 포함)를 함께 가져오는 채팅방 목록
    List<ChatRoom> findAllRoomsWithParticipantsInfoByUserId(Long userId);
}

