package com.beyond.homs.chat.controller;

import com.beyond.homs.chat.dto.ChatMessageDto;
import com.beyond.homs.chat.dto.ChatRoomDto;
import com.beyond.homs.chat.dto.ChatRoomListDto; // Import the updated DTO
import com.beyond.homs.chat.dto.ParticipantDto; // Import the new ParticipantDto
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
     * otherUserEmail을 통해 상대방을 찾습니다.
     * principal.getName()에는 userName("admin", "kim" 등)이 담겨 있다고 가정
     */
    @PostMapping("/room")
    public ChatRoomDto createOrGetRoom(@RequestParam String otherUserEmail, Principal principal) { // Changed from Long otherUserId
        if (principal == null) {
            throw new IllegalArgumentException("인증 정보가 없습니다. 먼저 로그인해주세요.");
        }

        // REST 요청이므로 principal.getName()은 userName을 반환합니다.
        String currentUserName = principal.getName();

        // userName으로 현재 User 엔티티 조회
        User currentUser = userRepository.findByUserName(currentUserName)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + currentUserName));
        Long currentUserId = currentUser.getUserId();

        // otherUserEmail로 상대방 User 엔티티 조회
        User otherUser = userRepository.findByManagerEmail(otherUserEmail) // Find by managerEmail
                .orElseThrow(() -> new IllegalArgumentException("상대방 이메일로 사용자를 찾을 수 없습니다: " + otherUserEmail));
        Long otherUserId = otherUser.getUserId(); // Get userId from the found user

        // 자기 자신과 채팅 시도 방지
        if (currentUserId.equals(otherUserId)) {
            throw new IllegalArgumentException("자기 자신과 채팅방을 만들 수 없습니다.");
        }

        // ChatService에 방 생성 또는 조회 요청
        ChatRoom room = chatService.getOrCreateRoom(currentUserId, otherUserId);

        // ChatRoomDto 반환 (roomId, user1Id, user2Id)
        return new ChatRoomDto(
                room.getRoomId(),
                room.getUser1().getUserId(),
                room.getUser2().getUserId()
        );
    }


    /**
     * 과거 모든 메시지 조회 (REST 요청)
     */
    @GetMapping("/room/{roomId}/messages")
    public List<ChatMessageDto> getAllMessages(@PathVariable String roomId, Principal principal) {
        if (principal == null) {
            throw new IllegalArgumentException("인증 정보가 없습니다. 먼저 로그인해주세요.");
        }
        // Principal을 통해 현재 사용자 인증 여부만 확인합니다.
        // 이 메서드에서는 메시지 내용에 userId를 직접 포함하지 않으므로 추가적인 userId 추출은 필요 없습니다.
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

        Long currentUserId = currentUser.getUserId();

        // ChatService의 새로운 메서드를 호출하여 User 및 Company 정보가 Fetch된 ChatRoom 목록을 가져옵니다.
        List<ChatRoom> rooms = chatService.findAllRoomsWithParticipantsInfoByUserId(currentUserId);

        return rooms.stream().map(room -> {
            ChatMessage lastMessage = chatService.findLastMessageByRoomId(room.getRoomId());

            // User 엔티티에서 필요한 정보 추출하여 ParticipantDto 생성
            User user1Entity = room.getUser1();
            ParticipantDto user1Dto = new ParticipantDto(
                    user1Entity.getUserId(),
                    user1Entity.getManagerName(), // managerName 사용
                    user1Entity.getCompany() != null ? user1Entity.getCompany().getCompanyName() : null // companyName 사용
            );

            User user2Entity = room.getUser2();
            ParticipantDto user2Dto = new ParticipantDto(
                    user2Entity.getUserId(),
                    user2Entity.getManagerName(), // managerName 사용
                    user2Entity.getCompany() != null ? user2Entity.getCompany().getCompanyName() : null // companyName 사용
            );

            return new ChatRoomListDto(
                    room.getRoomId(),
                    user1Dto, // ParticipantDto 객체 전달
                    user2Dto, // ParticipantDto 객체 전달
                    lastMessage != null ? lastMessage.getContent() : null,
                    lastMessage != null ? lastMessage.getSentAt() : null
            );
        }).collect(Collectors.toList());
    }
}





