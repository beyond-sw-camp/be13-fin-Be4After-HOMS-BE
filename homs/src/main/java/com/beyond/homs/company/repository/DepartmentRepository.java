package com.beyond.homs.company.repository;

import com.beyond.homs.company.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> { }
