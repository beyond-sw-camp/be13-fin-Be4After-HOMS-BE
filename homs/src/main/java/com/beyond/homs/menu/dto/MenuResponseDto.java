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

    private String image;

    private String path;

    private Boolean buy;

    private Boolean delivery;

    private Boolean materials;

    private Boolean sales;

    private Long parentId;
}
