package com.beyond.homs.chat.entity;

import com.beyond.homs.user.entity.User;  // 이미 있는 User 엔티티
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "chat_room")
@Getter
@NoArgsConstructor
public class ChatRoom {

    @Id
    @Column(name = "room_id", nullable = false, updatable = false)
    private String roomId;  // UUID 문자열 (예: UUID.randomUUID().toString())

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user1_id")
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user2_id")
    private User user2;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public ChatRoom(User user1, User user2) {
        this.roomId = UUID.randomUUID().toString();
        this.user1 = user1;
        this.user2 = user2;
        this.createdAt = LocalDateTime.now();
    }
}

