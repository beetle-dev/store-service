package com.cafe.storeservice.dto;

import com.cafe.storeservice.domain.MenuCategory;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuSearchDto extends SearchDto {
    private String menuCategory;
    private String name;
    private Boolean isActive;

    private MenuCategory menuCategoryEntity;
}
