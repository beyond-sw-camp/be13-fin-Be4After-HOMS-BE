package com.beyond.homs.menu.entity;

import com.beyond.homs.company.entity.Department;
import com.beyond.homs.menu.dto.MenuRequestDto;
import com.beyond.homs.product.dto.ProductCategoryRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"parent","children"})
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "sort_no")
    private int sortNo;

    @Column(name = "image")
    private String image;

    @Column(name = "path")
    private String path;

    @Column(name = "is_buy")
    private Boolean buy;

    @Column(name = "is_delivery")
    private Boolean delivery;

    @Column(name = "is_materials")
    private Boolean materials;

    @Column(name = "is_sales")
    private Boolean sales;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "menu_id_parent", nullable = true)
    private Menu parent;

    @OneToMany(mappedBy = "parent",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy("sortNo ASC")
    private List<Menu> children = new ArrayList<>();

    @Builder
    public Menu(String menuName, int sortNo, String image, String path, Boolean buy, Boolean delivery, Boolean materials, Boolean sales, Menu parent) {
        this.menuName = menuName;
        this.sortNo = sortNo;
        this.image = image;
        this.path = path;
        this.buy = buy;
        this.delivery = delivery;
        this.materials = materials;
        this.sales = sales;
        this.parent = parent;
    }

    public void updateMenu(MenuRequestDto requestDto) {
        this.menuName = requestDto.getMenuName();
        this.sortNo = requestDto.getSortNo();
        this.image = requestDto.getImage();
        this.path = requestDto.getPath();
        this.buy = requestDto.getBuy();
        this.delivery = requestDto.getDelivery();
        this.materials = requestDto.getMaterials();
        this.sales = requestDto.getSales();
    }
}
