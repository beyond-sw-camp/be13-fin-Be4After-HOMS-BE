package com.beyond.homs.wms.repository;

import com.beyond.homs.wms.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddRepository extends JpaRepository<DeliveryAddress, Long> {
}
