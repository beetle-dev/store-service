package com.cafe.storeservice.dto.menu;

import com.cafe.storeservice.domain.menu.Menu;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
