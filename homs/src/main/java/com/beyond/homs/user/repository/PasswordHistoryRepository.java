package com.beyond.homs.user.repository;

import com.beyond.homs.user.entity.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> { }
