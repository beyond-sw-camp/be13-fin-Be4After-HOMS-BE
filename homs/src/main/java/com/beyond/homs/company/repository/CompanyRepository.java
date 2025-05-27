package com.beyond.homs.company.repository;

import com.beyond.homs.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
