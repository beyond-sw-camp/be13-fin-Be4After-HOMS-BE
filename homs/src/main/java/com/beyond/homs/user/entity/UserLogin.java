package com.beyond.homs.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_login")
@Getter
@NoArgsConstructor
public class UserLogin {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;


    @Setter
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "is_locked_out", nullable = false)
    private boolean lockedOut;

    @Builder
    public UserLogin(User user, String passwordHash, boolean lockedOut) {
        this.user = user;
        this.passwordHash = passwordHash;
        this.lockedOut = lockedOut;
    }
}
