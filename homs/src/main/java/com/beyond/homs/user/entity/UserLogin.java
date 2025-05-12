package com.beyond.homs.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

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


    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "is_locked_out", nullable = false)
    private boolean lockedOut;
}
