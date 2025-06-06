package com.beyond.homs.menu.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.menu.dto.MenuListDto;
import com.beyond.homs.menu.dto.MenuRequestDto;
import com.beyond.homs.menu.dto.MenuResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "메뉴 관리 API", description = "메뉴 관리 API 목록")
public interface MenuController {
    @Operation(summary = "전체 메뉴 목록 조회", description = "전체 메뉴 목록을 조회합니다.")
    ResponseEntity<ResponseDto<List<MenuListDto>>> MenuList();

    @Operation(summary = "DEPT 별 메뉴 목록 조회", description = "DEPT 별 메뉴 목록을 조회합니다.")
    ResponseEntity<ResponseDto<List<MenuListDto>>> MenuListByDept(
            @RequestParam String menuName);

    @Operation(summary = "메뉴 등록", description = "메뉴를 등록합니다.")
    ResponseEntity<ResponseDto<MenuResponseDto>> createMenu(
            @RequestBody MenuRequestDto MenuRequestDto);

    @Operation(summary = "메뉴 수정", description = "메뉴를 수정합니다.")
    ResponseEntity<ResponseDto<MenuResponseDto>> updateMenu(
            @PathVariable Long menuId,
            @RequestBody MenuRequestDto MenuRequestDto);

    @Operation(summary = "메뉴 제거", description = "메뉴를 제거합니다.")
    ResponseEntity<ResponseDto<Void>> deleteMenu(
            @PathVariable Long menuId);
}
