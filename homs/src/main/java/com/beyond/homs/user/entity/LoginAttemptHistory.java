package com.beyond.homs.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "login_attempt_history")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class LoginAttemptHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attempt_id")
    private Long attemptId;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "is_login_success", nullable = false)
    private boolean loginSuccess;

    @Column(name = "attempt_at", nullable = false)
    @CreatedDate
    private LocalDateTime attemptAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserLogin userLogin;

    @Builder
    public LoginAttemptHistory(String passwordHash, boolean loginSuccess, UserLogin userLogin) {
        this.passwordHash = passwordHash;
        this.loginSuccess = loginSuccess;
        this.userLogin = userLogin;
    }
}
