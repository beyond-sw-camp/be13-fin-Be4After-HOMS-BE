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

    private final Boolean buy;

    private final Boolean delivery;

    private final Boolean materials;

    private final Boolean sales;

    private final Long parentId;

    private final List<MenuListDto> children;

    private MenuListDto(Long menuId, String menuName, int sortNo, Boolean buy, Boolean delivery, Boolean materials, Boolean sales, Long parentId, List<MenuListDto> children)
    {
        this.menuId = menuId;
        this.menuName = menuName;
        this.sortNo = sortNo;
        this.buy = buy;
        this.delivery = delivery;
        this.materials = materials;
        this.sales = sales;
        this.parentId = parentId;
        this.children = children;
    }

    // 자식 카테고리까지 재귀 변환하기 위하여 of 사용
    public static MenuListDto of(Menu menu) {
        return new MenuListDto(
                menu.getMenuId(),
                menu.getMenuName(),
                menu.getSortNo(),
                menu.getBuy(),
                menu.getDelivery(),
                menu.getMaterials(),
                menu.getSales(),
                menu.getParent() != null ? menu.getParent().getMenuId() : null,
                menu.getChildren().stream()
                        .map(MenuListDto::of)
                        .collect(Collectors.toList())
        );
    }
}
