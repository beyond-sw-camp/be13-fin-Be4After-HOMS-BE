package com.beyond.homs.company.repository;

import com.beyond.homs.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByCompanyName(String companyName);

    Company findByCompanyId(Long companyId);
}
