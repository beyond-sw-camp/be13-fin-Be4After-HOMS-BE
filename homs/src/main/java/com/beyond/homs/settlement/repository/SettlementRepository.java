package com.beyond.homs.settlement.repository;

import com.beyond.homs.settlement.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {
    List<Settlement> findAllByOrderUserUserId(Long userId);
}
