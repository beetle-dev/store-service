package com.cafe.storeservice.dto;

import com.cafe.storeservice.domain.MenuCategories;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MenuReqDto {
    private String menuCategory;

    @NotEmpty
    private String name;

    private String description;

    @NotNull
    private BigDecimal price;
    private BigDecimal cost;
    private String imageUrl;
    private Boolean isActive;

    private MenuCategories menuCategories;
}
