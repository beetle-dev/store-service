package com.cafe.storeservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuSearchDto extends SearchDto {
    private Long menuCategoryId;
    private String name;
    private Boolean isActive;
}
