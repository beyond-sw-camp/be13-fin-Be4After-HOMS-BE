package com.beyond.homs.menu.repository;

import com.beyond.homs.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByParentIsNullOrderBySortNoAsc();

    List<Menu> findByParent(Menu parent);

    @Query("SELECT m FROM Menu m WHERE m.parent IS NOT NULL AND " +
        "((:deptId = 1 AND m.buy = true) OR " +
        " (:deptId = 2 AND m.delivery = true) OR " +
        " (:deptId = 3 AND m.materials = true) OR " +
        " (:deptId = 4 AND m.sales = true)) " +
        "ORDER BY m.sortNo ASC")
    List<Menu> findMenusByDeptId(@Param("deptId") Long deptId);
}
