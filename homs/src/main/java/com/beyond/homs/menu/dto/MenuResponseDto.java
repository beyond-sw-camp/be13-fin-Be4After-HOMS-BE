package com.beyond.homs.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MenuResponseDto {

    private Long menuId;

    private String menuName;

    private int sortNo;

    private Long parentId;

    private Long deptId;
}
