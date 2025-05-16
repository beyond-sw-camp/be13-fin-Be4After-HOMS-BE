package com.beyond.homs.user.repository;

import com.beyond.homs.user.entity.User;
import com.beyond.homs.user.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
}
