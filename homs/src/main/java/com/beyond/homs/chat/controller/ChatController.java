package com.beyond.homs.chat.controller;

import com.beyond.homs.chat.dto.ChatMessageDto;
import com.beyond.homs.chat.dto.ChatRoomDto;
import com.beyond.homs.chat.entity.ChatMessage;
import com.beyond.homs.chat.entity.ChatRoom;
import com.beyond.homs.chat.service.ChatService;
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
@RequestMapping("/api/v1/chatter")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    /**
     * 1-1 채팅방 생성 혹은 조회 (프론트에서 방을 만들거나 입장할 때 호출)
     */
    @PostMapping("/api/chat/room")
    public ChatRoomDto createOrGetRoom(@RequestParam Long otherUserId, Principal principal) {
        // principal.getName()은 JWT에서 꺼낸 사용자 PK(여기선 "3" 같은 값)
        Long currentUserId = Long.parseLong(principal.getName());
        Long userId2 = otherUserId;

        ChatRoom room = chatService.getOrCreateRoom(currentUserId, userId2);
        return new ChatRoomDto(room.getRoomId(), room.getUser1().getUserId(), room.getUser2().getUserId());
    }

    /**
     * 과거 메시지 불러오기 (방에 들어갔을 때 전체 혹은 페이징으로 호출)
     */
    @GetMapping("/api/chat/room/{roomId}/messages")
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
     * WebSocket을 통한 실시간 메시지 처리
     * 클라이언트 -> /pub/chat.sendMessage 로 보낸 메시지를 받아서
     * 1) DB에 저장, 2) /sub/chat/room/{roomId}으로 브로드캐스트
     */
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageDto dto, Principal principal) {
        Long senderId = Long.parseLong(principal.getName());
        // 1) DB에 저장
        ChatMessage saved = chatService.saveMessage(dto.getRoomId(), senderId, dto.getContent());

        // 2) 브로드캐스트
        ChatMessageDto broadcastDto = new ChatMessageDto(
                saved.getMessageId(),
                saved.getRoomId(),
                saved.getSender().getUserId(),
                saved.getContent(),
                saved.getSentAt()
        );
        messagingTemplate.convertAndSend(
                "/sub/chat/room/" + saved.getRoomId(),
                broadcastDto
        );
    }
}

