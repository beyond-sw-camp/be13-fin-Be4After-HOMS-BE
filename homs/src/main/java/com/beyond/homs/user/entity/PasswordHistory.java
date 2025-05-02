package com.beyond.homs.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_history")
@Getter
@NoArgsConstructor
public class PasswordHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pw_history_id")
    private Long pwHistoryId;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "retired_at", nullable = false)
    @CreatedDate
    private LocalDateTime retiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Builder
    public PasswordHistory(String passwordHash, User user) {
        this.passwordHash = passwordHash;
        this.user = user;
    }
}
