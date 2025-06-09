package com.beyond.homs.chat.repository;

import com.beyond.homs.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

    // 두 유저 간의 채팅방이 이미 존재하는지 확인하기 위한 메서드
    Optional<ChatRoom> findByUser1_UserIdAndUser2_UserId(Long user1Id, Long user2Id);

    // user1과 user2 순서가 바뀐 경우도 찾기
    Optional<ChatRoom> findByUser2_UserIdAndUser1_UserId(Long user2Id, Long user1Id);

    // 기존 derived query (다른 곳에서 사용될 수 있으므로 유지)
    List<ChatRoom> findByUser1_UserIdOrUser2_UserId(Long user1Id, Long user2Id);

    // 새로운 쿼리: ChatRoom과 user1, user2, 그리고 각 user의 Company를 JOIN FETCH로 함께 가져옴
    @Query("SELECT cr FROM ChatRoom cr JOIN FETCH cr.user1 u1 JOIN FETCH u1.company JOIN FETCH cr.user2 u2 JOIN FETCH u2.company WHERE u1.userId = :userId OR u2.userId = :userId")
    List<ChatRoom> findAllRoomsWithUsersAndCompaniesByUserId(@Param("userId") Long userId);
}
