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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository; // User 엔티티 조회용

    /**
     * 1:1 채팅방 조회(또는 없으면 생성)
     * @param userId1 첫 번째 유저(ID)
     * @param userId2 두 번째 유저(ID)
     */
    @Transactional
    public ChatRoom getOrCreateRoom(Long userId1, Long userId2) {
        // 두 사용자 간 이미 방이 있는지 확인 (순서 상관 없이)
        return chatRoomRepository
                .findByUser1_UserIdAndUser2_UserId(userId1, userId2)
                .or(() -> chatRoomRepository.findByUser2_UserIdAndUser1_UserId(userId1, userId2))
                .orElseGet(() -> {
                    // 없으면 신규 생성
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

    /**
     * 메시지 저장
     */
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

    /**
     * 특정 방(roomId)의 모든 메시지 조회 (오래된 순)
     */
    public List<ChatMessage> findAllMessages(String roomId) {
        return chatMessageRepository.findByRoomIdOrderBySentAtAsc(roomId);
    }

    /**
     * 페이징: 최신 N개 메시지 조회 (필요하다면 프론트에서 호출)
     * ex) Pageable page = PageRequest.of(0, 50);
     */
    public List<ChatMessage> findRecentMessages(String roomId, int size) {
        PageRequest page = PageRequest.of(0, size);
        // 내림차순으로 가져온 뒤, (예: 최신 50개), 호출 쪽에서 reverse 처리하거나 직접 오름차순으로 재정렬 가능
        return chatMessageRepository.findByRoomIdOrderBySentAtDesc(roomId, page);
    }
}
