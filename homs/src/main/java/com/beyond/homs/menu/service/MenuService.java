package com.beyond.homs.menu.service;

import com.beyond.homs.menu.dto.MenuListDto;
import com.beyond.homs.menu.dto.MenuRequestDto;
import com.beyond.homs.menu.dto.MenuResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MenuService {
    // 메뉴 조회
    List<MenuListDto> getMenus();

    // 메뉴 등록
    @Transactional
    MenuResponseDto createMenu(MenuRequestDto requestDto);

    // 메뉴 수정
    @Transactional
    MenuResponseDto updateMenu(Long menuId, MenuRequestDto requestDto);

    // 메뉴 삭제
    @Transactional
    void deleteMenu(Long menuId);

}
