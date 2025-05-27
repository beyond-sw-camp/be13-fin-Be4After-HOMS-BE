package com.beyond.homs.menu.service;


import com.beyond.homs.company.entity.Department;
import com.beyond.homs.company.repository.DepartmentRepository;
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
    private final DepartmentRepository departmentRepository;

    // 메뉴 조회
    @Override
    public List<MenuListDto> getMenus() {
        //JPA는 메서드 이름을 보면 내부에서 자동으로 JPQL 쿼리를 만들어 실행 (최상위 루트 카테고리만 가져옴)
        List<Menu> rootCategories = menuRepository.findByParentIsNullOrderBySortNoAsc();

        return rootCategories.stream()
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

        // Department 조회
        Department department = departmentRepository.findById(requestDto.getDeptId())
                .orElseThrow(() -> new IllegalArgumentException("부서 정보가 존재하지 않습니다."));

        Menu menu = Menu.builder()
                .menuName(requestDto.getMenuName())
                .sortNo(requestDto.getSortNo())
                .parent(parent)
                .department(department)
                .build();

        Menu saved = menuRepository.save(menu);

        return MenuResponseDto.builder()
                .menuId(saved.getMenuId())
                .menuName(saved.getMenuName())
                .sortNo(saved.getSortNo())
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
                .parentId(menu.getParent().getMenuId())
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
