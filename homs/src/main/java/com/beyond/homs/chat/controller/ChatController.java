package com.beyond.homs.chat.controller;

import com.beyond.homs.chat.dto.ChatMessageDto;
import com.beyond.homs.chat.dto.ChatRoomDto;
import com.beyond.homs.chat.entity.ChatMessage;
import com.beyond.homs.chat.entity.ChatRoom;
import com.beyond.homs.chat.service.ChatService;
import com.beyond.homs.user.entity.User;
import com.beyond.homs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    /**
     * 1:1 채팅방 생성 또는 기존 방 조회
     * principal.getName()에는 userName("admin", "kim" 등)이 담겨 있다고 가정
     */
    @PostMapping("/room")
    public ChatRoomDto createOrGetRoom(@RequestParam Long otherUserId, Principal principal) {
        // 1) Principal이 null인지 체크
        if (principal == null) {
            throw new IllegalArgumentException("인증 정보가 없습니다. 먼저 로그인해주세요.");
        }

        // 2) principal.getName() → userName("admin", "kim" 등)
        String currentUserName = principal.getName();

        // 3) userName으로 User 엔티티 조회
        User currentUser = userRepository.findByUserName(currentUserName)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + currentUserName));

        // 4) User 엔티티에서 userId 꺼내기
        Long currentUserId = currentUser.getUserId();

        // 5) otherUserId가 실제 DB에 존재하는지 한 번 더 확인 (선택 사항)
        userRepository.findById(otherUserId)
                .orElseThrow(() -> new IllegalArgumentException("상대 사용자를 찾을 수 없습니다: " + otherUserId));

        // 6) ChatService에 방 생성 또는 조회 요청
        ChatRoom room = chatService.getOrCreateRoom(currentUserId, otherUserId);

        // 7) ChatRoomDto 반환 (roomId, user1Id, user2Id)
        return new ChatRoomDto(
                room.getRoomId(),
                room.getUser1().getUserId(),
                room.getUser2().getUserId()
        );
    }

    /**
     * 과거 모든 메시지 조회
     */
    @GetMapping("/room/{roomId}/messages")
    public List<ChatMessageDto> getAllMessages(@PathVariable String roomId) {
        List<ChatMessage> messages = chatService.findAllMessages(roomId);
        return messages.stream()
                .map(msg -> new ChatMessageDto(
                        msg.getMessageId(),
                        msg.getRoomId(),
                        msg.getSender().getUserId(),
                        msg.getContent(),
                        msg.getSentAt()
                ))
                .collect(Collectors.toList());
    }

    /**
     * WebSocket(STOMP) 통해 실시간 메시지 전송
     * 클라이언트가 "/pub/sendMessage"로 보낸 메시지를 받아서
     * 1) DB 저장, 2) "/sub/chat/room/{roomId}"으로 브로드캐스트
     */
    @MessageMapping("/sendMessage")
    public void sendMessage(ChatMessageDto dto, Principal principal) {
        // 1) Principal이 null인지 체크
        if (principal == null) {
            throw new IllegalArgumentException("인증 정보가 없습니다. 먼저 로그인해주세요.");
        }

        // 2) principal.getName()에는 userId(예: "2")가 문자열로 담겨 있으므로 Long으로 파싱
        Long senderId;
        try {
            senderId = Long.valueOf(principal.getName());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("유효하지 않은 사용자 ID 형식: " + principal.getName());
        }

        // 3) userId로 User 엔티티 조회
        User currentUser = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + senderId));

        // 4) DB에 메시지 저장
        ChatMessage saved = chatService.saveMessage(dto.getRoomId(), senderId, dto.getContent());

        // 5) 브로드캐스트할 DTO 준비
        ChatMessageDto broadcastDto = new ChatMessageDto(
                saved.getMessageId(),
                saved.getRoomId(),
                saved.getSender().getUserId(),
                saved.getContent(),
                saved.getSentAt()
        );

        // 6) "/sub/chat/room/{roomId}"로 실시간 브로드캐스트
        messagingTemplate.convertAndSend("/sub/chat/room/" + saved.getRoomId(), broadcastDto);
    }
}




