package com.cafe.storeservice.dto;

import com.cafe.storeservice.domain.Menu;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class MenuResDto {
    private Long id;
    private String menuCategory;
    private Long menuCategoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal cost;
    private Boolean isActive;
    private String imageUrl;

    public static MenuResDto from(Menu menu, String imageUrl) {
        return MenuResDto.builder()
                .id(menu.getId())
                .menuCategory(menu.getMenuCategory() != null ? menu.getMenuCategory().getName() : null)
                .menuCategoryId(menu.getMenuCategory() != null ? menu.getMenuCategory().getId() : null)
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .cost(menu.getCost())
                .isActive(menu.getIsActive())
                .imageUrl(imageUrl)
                .build();
    }
}
