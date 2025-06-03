package com.beyond.homs.wms.repository;

import java.util.List;
import com.beyond.homs.wms.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddRepository extends JpaRepository<DeliveryAddress, Long> {
    List<DeliveryAddress> findByCompany_CompanyId(Long companyId);
}
