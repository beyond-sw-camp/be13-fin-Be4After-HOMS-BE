package com.beyond.homs.menu.dto;

import lombok.Getter;

@Getter
public class MenuRequestDto {
    private String menuName;

    private int sortNo;

    private Long parentId;

    private Long deptId;
}
