package com.beyond.homs.chat.repository;

import com.beyond.homs.chat.entity.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // 특정 방(roomId)의 메시지들을 시간순(오래된 순)으로 조회
    List<ChatMessage> findByRoomIdOrderBySentAtAsc(String roomId);

    // 페이징용: 마지막 N개 메시지 가져오기 (예: 최신 50개)
    List<ChatMessage> findByRoomIdOrderBySentAtDesc(String roomId, Pageable pageable);
}
