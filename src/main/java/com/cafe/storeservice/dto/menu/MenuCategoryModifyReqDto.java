package com.cafe.storeservice.dto.menu;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuCategoryModifyReqDto {
    private String name;
    private Integer sortOrder;
    private Boolean isActive;
}
