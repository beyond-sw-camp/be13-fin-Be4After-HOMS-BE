package com.beyond.homs.menu.service;

import com.beyond.homs.menu.dto.MenuListDto;
import com.beyond.homs.menu.dto.MenuRequestDto;
import com.beyond.homs.menu.dto.MenuResponseDto;
import com.beyond.homs.menu.entity.Menu;
import com.beyond.homs.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{
    private final MenuRepository menuRepository;

    // 메뉴 조회
    @Override
    public List<MenuListDto> getMenus() {
        //JPA는 메서드 이름을 보면 내부에서 자동으로 JPQL 쿼리를 만들어 실행 (최상위 루트 카테고리만 가져옴)
        List<Menu> rootCategories = menuRepository.findByParentIsNullOrderBySortNoAsc();

        return rootCategories.stream()
                .map(MenuListDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuListDto> getMenusByDept(String menuEnum) {
        List<Menu> rootMenus = menuRepository.findByParentIsNullOrderBySortNoAsc();
        List<Menu> filteredChildren = menuRepository.findMenusByDeptMenu(menuEnum);
        System.out.println(filteredChildren);
        System.out.println(rootMenus);
        System.out.println("1111111111111111");
        for (Menu root : rootMenus) {
            List<Menu> children = filteredChildren.stream()
                .filter(child -> child.getParent().getMenuId().equals(root.getMenuId()))
                .collect(Collectors.toList());
            root.getChildren().clear();
            root.getChildren().addAll(children);
        }

        return rootMenus.stream()
            .map(MenuListDto::of)
            .collect(Collectors.toList());
    }

    // 메뉴 등록
    @Override
    public MenuResponseDto createMenu(MenuRequestDto requestDto) {
        Menu parent = null;

        if (requestDto.getParentId() != null) {
            parent = menuRepository.findById(requestDto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리가 존재하지 않습니다."));
        }

        Menu menu = Menu.builder()
                .menuName(requestDto.getMenuName())
                .sortNo(requestDto.getSortNo())
                .image(requestDto.getImage())
                .path(requestDto.getPath())
                .buy(requestDto.getBuy())
                .delivery(requestDto.getDelivery())
                .materials(requestDto.getMaterials())
                .sales(requestDto.getSales())
                .parent(parent)
                .build();

        Menu saved = menuRepository.save(menu);

        return MenuResponseDto.builder()
                .menuId(saved.getMenuId())
                .menuName(saved.getMenuName())
                .sortNo(saved.getSortNo())
                .image(saved.getImage())
                .path(saved.getPath())
                .buy(saved.getBuy())
                .delivery(saved.getDelivery())
                .materials(saved.getMaterials())
                .sales(saved.getSales())
                .parentId(saved.getParent() != null ? saved.getParent().getMenuId() : null)
                .build();
    }

    // 메뉴 수정
    @Override
    public MenuResponseDto updateMenu(Long menuId, MenuRequestDto requestDto) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("해당 메뉴가 존재하지 않습니다."));

        menu.updateMenu(requestDto);
        menuRepository.save(menu);

        return MenuResponseDto.builder()
                .menuId(menu.getMenuId())
                .menuName(menu.getMenuName())
                .sortNo(menu.getSortNo())
                .image(menu.getImage())
                .path(menu.getPath())
                .buy(menu.getBuy())
                .delivery(menu.getDelivery())
                .materials(menu.getMaterials())
                .sales(menu.getSales())
                .parentId(menu.getParent() != null ? menu.getParent().getMenuId() : null)
                .build();
    }

    // 메뉴 삭제
    @Override
    public void deleteMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("해당 메뉴가 존재하지 않습니다."));

        // 자식 카테고리 조회
        List<Menu> children = menuRepository.findByParent(menu);

        // 자식이 존재하면 먼저 삭제
        if(!children.isEmpty()){
            menuRepository.deleteAll(children);
        }

        // 부모 카테고리 삭제 or 자식인 경우 단독 삭제
        menuRepository.delete(menu);
    }
}
