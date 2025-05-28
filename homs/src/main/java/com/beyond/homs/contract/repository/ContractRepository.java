package com.beyond.homs.contract.repository;

import com.beyond.homs.contract.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContractRepository extends JpaRepository<Contract, Long> {
//    Page<Contract> findByCompany_CompanyNameContaining(String companyName, Pageable pageable);
@Query("""
      SELECT c
        FROM Contract c
        JOIN c.company comp
        JOIN c.product prod
        JOIN prod.category cat
       WHERE (:keyword IS NULL
              OR LOWER(comp.companyName)  LIKE LOWER(CONCAT('%', :keyword, '%'))
              OR LOWER(prod.productName)  LIKE LOWER(CONCAT('%', :keyword, '%'))
              OR LOWER(cat.categoryName)  LIKE LOWER(CONCAT('%', :keyword, '%'))
             )
      """)
Page<Contract> searchByKeyword(
        @Param("keyword") String keyword,
        Pageable pageable
);
}

