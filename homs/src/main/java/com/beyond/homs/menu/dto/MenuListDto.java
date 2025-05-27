package com.beyond.homs.menu.dto;

import com.beyond.homs.menu.entity.Menu;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MenuListDto {

    private final Long menuId;

    private final String menuName;

    private final int sortNo;

    private final Long deptId;

    private final List<MenuListDto> children;

    private MenuListDto(Long menuId, String menuName, int sortNo, Long deptId, List<MenuListDto> children)
    {
        this.menuId = menuId;
        this.menuName = menuName;
        this.sortNo = sortNo;
        this.deptId = deptId;
        this.children = children;
    }

    // 자식 카테고리까지 재귀 변환하기 위하여 of 사용
    public static MenuListDto of(Menu menu) {
        return new MenuListDto(
                menu.getMenuId(),
                menu.getMenuName(),
                menu.getSortNo(),
                menu.getDepartment().getDeptId(),
                menu.getChildren().stream()
                        .map(MenuListDto::of)
                        .collect(Collectors.toList())
        );
    }
}
