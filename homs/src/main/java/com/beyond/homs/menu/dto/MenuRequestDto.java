package com.beyond.homs.menu.dto;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class MenuRequestDto {
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
