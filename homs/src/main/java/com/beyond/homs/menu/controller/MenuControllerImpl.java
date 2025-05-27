package com.beyond.homs.menu.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.menu.dto.MenuListDto;
import com.beyond.homs.menu.dto.MenuRequestDto;
import com.beyond.homs.menu.dto.MenuResponseDto;
import com.beyond.homs.menu.service.MenuService;
import com.beyond.homs.product.controller.ProductCategoryController;
import com.beyond.homs.product.dto.ProductCategoryListDto;
import com.beyond.homs.product.dto.ProductCategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/menu/")
public class MenuControllerImpl implements MenuController {
    private final MenuService  menuService;

    //메뉴 목록 조회
    @GetMapping("/")
    @Override
    public ResponseEntity<ResponseDto<List<MenuListDto>>> MenuList() {
        List<MenuListDto> menuList = menuService.getMenus();

        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "메뉴 목록을 불러왔습니다.",
                        menuList
                ));
    }

    //메뉴 등록
    @PostMapping("/create")
    @Override
    public ResponseEntity<ResponseDto<MenuResponseDto>> createMenu(
            @RequestBody MenuRequestDto MenuRequestDto) {

        MenuResponseDto menu = menuService.createMenu(MenuRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ResponseDto<>(
                                HttpStatus.CREATED.value(),
                                "메뉴가 등록되었습니다.",
                                menu
                        ));
    }

    //메뉴 수정
    @PutMapping("/update/{menuId}")
    @Override
    public ResponseEntity<ResponseDto<MenuResponseDto>> updateMenu(
            @PathVariable Long menuId,
            @RequestBody MenuRequestDto MenuRequestDto) {
        MenuResponseDto menu = menuService.updateMenu(menuId, MenuRequestDto);
        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "메뉴가 수정되었습니다.",
                        menu
                ));
    }

    //메뉴 제거
    @DeleteMapping("/delete/{menuId}")
    @Override
    public ResponseEntity<ResponseDto<Void>> deleteMenu(
            @PathVariable Long menuId) {
        menuService.deleteMenu(menuId);

        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "메뉴가 삭제되었습니다.",
                        null
                ));
    }
}
