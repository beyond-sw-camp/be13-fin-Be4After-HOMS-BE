package com.beyond.homs.user.repository;

import com.beyond.homs.user.entity.User;
import com.beyond.homs.user.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    Optional<UserLogin> findByUser(User user);
}
