package com.beyond.homs.chat.controller;

import com.beyond.homs.chat.dto.ChatMessageDto;
import com.beyond.homs.chat.dto.ChatRoomDto;
import com.beyond.homs.chat.dto.ChatRoomListDto; // ChatRoomListDto 임포트 필요
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
     * 1:1 채팅방 생성 또는 기존 방 조회 (REST 요청)
     * principal.getName()에는 userName("admin", "kim" 등)이 담겨 있다고 가정
     */
    @PostMapping("/room")
    public ChatRoomDto createOrGetRoom(@RequestParam Long otherUserId, Principal principal) {
        if (principal == null) {
            throw new IllegalArgumentException("인증 정보가 없습니다. 먼저 로그인해주세요.");
        }

        // REST 요청이므로 principal.getName()은 userName을 반환합니다.
        String currentUserName = principal.getName();

        // userName으로 User 엔티티를 조회하여 userId를 얻습니다.
        User currentUser = userRepository.findByUserName(currentUserName)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + currentUserName));

        Long currentUserId = currentUser.getUserId();

        userRepository.findById(otherUserId)
                .orElseThrow(() -> new IllegalArgumentException("상대 사용자를 찾을 수 없습니다: " + otherUserId));

        ChatRoom room = chatService.getOrCreateRoom(currentUserId, otherUserId);

        return new ChatRoomDto(
                room.getRoomId(),
                room.getUser1().getUserId(),
                room.getUser2().getUserId()
        );
    }

    /**
     * 과거 모든 메시지 조회 (REST 요청)
     * principal.getName()에는 userName("admin", "kim" 등)이 담겨 있다고 가정
     */
    @GetMapping("/room/{roomId}/messages")
    public List<ChatMessageDto> getAllMessages(@PathVariable String roomId, Principal principal) { // Principal 추가
        if (principal == null) {
            throw new IllegalArgumentException("인증 정보가 없습니다. 먼저 로그인해주세요.");
        }
        // 이 메서드에서는 현재 사용자의 userId를 직접 사용하지 않으므로, Principal 검증만 합니다.
        // 만약 메시지 조회 시 현재 사용자의 권한 검증이 필요하다면,
        // principal.getName()으로 userId를 얻어 추가 로직을 구현할 수 있습니다.
        // 현재는 roomId만으로 메시지를 조회하므로 Principal 사용은 선택 사항입니다.
        // (다만, @AuthenticationPrincipal 등을 사용하면 더 깔끔합니다.)

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
     * WebSocket(STOMP) 통해 실시간 메시지 전송 (@MessageMapping)
     * 클라이언트가 "/pub/sendMessage"로 보낸 메시지를 받아서
     * 1) DB 저장, 2) "/sub/chat/room/{roomId}"으로 브로드캐스트
     * principal.getName()에는 userId(예: "2")가 문자열로 담겨 있다고 가정 (StompHandler에 의해)
     */
    @MessageMapping("/sendMessage")
    public void sendMessage(ChatMessageDto dto, Principal principal) {
        if (principal == null) {
            throw new IllegalArgumentException("인증 정보가 없습니다. 먼저 로그인해주세요.");
        }

        // WebSocket 메시지 처리이므로 StompHandler에 의해 principal.getName()은 userId(문자열)입니다.
        Long senderId;
        try {
            senderId = Long.valueOf(principal.getName());
        } catch (NumberFormatException e) {
            // 이 예외는 StompHandler가 올바르게 작동하지 않거나,
            // Principal 설정에 문제가 있을 때 발생할 수 있습니다.
            throw new IllegalArgumentException("유효하지 않은 사용자 ID 형식 (WebSocket): " + principal.getName(), e);
        }

        User currentUser = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + senderId));

        ChatMessage saved = chatService.saveMessage(dto.getRoomId(), senderId, dto.getContent());

        ChatMessageDto broadcastDto = new ChatMessageDto(
                saved.getMessageId(),
                saved.getRoomId(),
                saved.getSender().getUserId(),
                saved.getContent(),
                saved.getSentAt()
        );

        messagingTemplate.convertAndSend("/sub/chat/room/" + saved.getRoomId(), broadcastDto);
    }

    /**
     * 현재 사용자가 참여하고 있는 모든 채팅방 목록 조회 (REST 요청)
     * principal.getName()에는 userName("admin", "kim" 등)이 담겨 있다고 가정
     */
    @GetMapping("/rooms")
    public List<ChatRoomListDto> getAllChatRooms(Principal principal) {
        if (principal == null) {
            throw new IllegalArgumentException("인증 정보가 없습니다. 먼저 로그인해주세요.");
        }

        // REST 요청이므로 principal.getName()은 userName을 반환합니다.
        String currentUserName = principal.getName();

        // userName으로 User 엔티티를 조회하여 userId를 얻습니다.
        User currentUser = userRepository.findByUserName(currentUserName)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + currentUserName));

        Long currentUserId = currentUser.getUserId(); // User 엔티티에서 userId를 가져옵니다.

        List<ChatRoom> rooms = chatService.findAllRoomsByUserId(currentUserId);

        return rooms.stream().map(room -> {
            ChatMessage lastMessage = chatService.findLastMessageByRoomId(room.getRoomId());

            return new ChatRoomListDto(
                    room.getRoomId(),
                    room.getUser1().getUserId(),
                    room.getUser2().getUserId(),
                    lastMessage != null ? lastMessage.getContent() : null,
                    lastMessage != null ? lastMessage.getSentAt() : null
            );
        }).collect(Collectors.toList());
    }
}




