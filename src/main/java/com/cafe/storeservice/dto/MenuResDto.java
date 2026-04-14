package com.cafe.storeservice.dto;

import com.cafe.storeservice.domain.MenuCategories;
import com.cafe.storeservice.domain.Menus;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class MenuResDto {
    private Long id;
    private MenuCategories menuCategories;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal cost;
    private Boolean isActive;
    private String imageUrl;

    public static MenuResDto from(Menus menus) {
        return MenuResDto.builder()
                .id(menus.getId())
                .menuCategories(menus.getMenuCategories())
                .name(menus.getName())
                .description(menus.getDescription())
                .price(menus.getPrice())
                .cost(menus.getCost())
                .isActive(menus.getIsActive())
                .imageUrl(menus.getImageUrl())
                .build();
    }
}
