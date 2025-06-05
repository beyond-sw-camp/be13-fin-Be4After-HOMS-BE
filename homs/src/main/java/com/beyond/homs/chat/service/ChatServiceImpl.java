package com.beyond.homs.chat.service;

import com.beyond.homs.chat.entity.ChatMessage;
import com.beyond.homs.chat.entity.ChatRoom;
import com.beyond.homs.chat.repository.ChatMessageRepository;
import com.beyond.homs.chat.repository.ChatRoomRepository;
import com.beyond.homs.user.entity.User;
import com.beyond.homs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatRoom getOrCreateRoom(Long userId1, Long userId2) {
        return chatRoomRepository
                .findByUser1_UserIdAndUser2_UserId(userId1, userId2)
                .or(() -> chatRoomRepository.findByUser2_UserIdAndUser1_UserId(userId1, userId2))
                .orElseGet(() -> {
                    User user1 = userRepository.findById(userId1)
                            .orElseThrow(() -> new IllegalArgumentException("user1을 찾을 수 없습니다."));
                    User user2 = userRepository.findById(userId2)
                            .orElseThrow(() -> new IllegalArgumentException("user2를 찾을 수 없습니다."));
                    ChatRoom newRoom = ChatRoom.builder()
                            .user1(user1)
                            .user2(user2)
                            .build();
                    return chatRoomRepository.save(newRoom);
                });
    }

    @Transactional
    public ChatMessage saveMessage(String roomId, Long senderId, String content) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("sender를 찾을 수 없습니다."));
        ChatMessage msg = ChatMessage.builder()
                .roomId(roomId)
                .sender(sender)
                .content(content)
                .build();
        return chatMessageRepository.save(msg);
    }

    public List<ChatMessage> findAllMessages(String roomId) {
        return chatMessageRepository.findByRoomIdOrderBySentAtAsc(roomId);
    }

    public List<ChatMessage> findRecentMessages(String roomId, int size) {
        PageRequest page = PageRequest.of(0, size);
        return chatMessageRepository.findByRoomIdOrderBySentAtDesc(roomId, page);
    }

    public ChatMessage findLastMessageByRoomId(String roomId) {
        return chatMessageRepository.findFirstByRoomIdOrderBySentAtDesc(roomId)
                .orElse(null);
    }

    @Override
    public List<ChatRoom> findAllRoomsWithParticipantsInfoByUserId(Long userId) {
        // 새로 정의한 Repository 메서드 호출
        return chatRoomRepository.findAllRoomsWithUsersAndCompaniesByUserId(userId);
    }
}