package com.beyond.homs.chat.repository;

import com.beyond.homs.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

    // 두 유저 간의 채팅방이 이미 존재하는지 확인하기 위한 메서드
    Optional<ChatRoom> findByUser1_UserIdAndUser2_UserId(Long user1Id, Long user2Id);

    // user1과 user2 순서가 바뀐 경우도 찾기
    Optional<ChatRoom> findByUser2_UserIdAndUser1_UserId(Long user2Id, Long user1Id);

    List<ChatRoom> findByUser1_UserIdOrUser2_UserId(Long user1Id, Long user2Id);
}
