package com.beyond.homs.user.repository;

import com.beyond.homs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    List<User> findAllByDeletedAtIsNull();
    Optional<User> findByUserIdAndDeletedAtIsNull(Long userId);
    Optional<User> findByManagerEmail(String managerEmail);
}
