package com.cafe.storeservice.dto.menu;

import com.cafe.storeservice.dto.SearchDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class MenuSearchDto extends SearchDto {
    private Long menuCategoryId;
    private String name;
    private Boolean isActive;
}
