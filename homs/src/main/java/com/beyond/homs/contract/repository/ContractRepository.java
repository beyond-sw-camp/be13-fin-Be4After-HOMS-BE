package com.beyond.homs.contract.repository;

import com.beyond.homs.contract.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    Page<Contract> findByCompany_CompanyNameContaining(String companyName, Pageable pageable);
}

