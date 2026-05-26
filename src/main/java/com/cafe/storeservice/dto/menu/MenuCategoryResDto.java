package com.cafe.storeservice.dto.menu;

import com.cafe.storeservice.domain.menu.MenuCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuCategoryResDto {
    private Long id;
    private String name;
    private Integer sortOrder;
    private Boolean isActive;

    public static MenuCategoryResDto from(MenuCategory category) {
        return MenuCategoryResDto.builder()
                .id(category.getId())
                .name(category.getName())
                .sortOrder(category.getSortOrder())
                .isActive(category.getIsActive())
                .build();
    }
}
