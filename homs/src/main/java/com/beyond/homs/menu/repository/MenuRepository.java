package com.beyond.homs.menu.repository;

import com.beyond.homs.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByParentIsNullOrderBySortNoAsc();

    List<Menu> findByParent(Menu parent);
}
