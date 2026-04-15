package com.cafe.storeservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuCategoryReqDto {
    @NotEmpty
    private String name;

    private Integer sortOrder;
    private Boolean isActive;
}
