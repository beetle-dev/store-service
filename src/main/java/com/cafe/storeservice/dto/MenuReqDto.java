package com.cafe.storeservice.dto;

import com.cafe.storeservice.domain.MenuCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Setter
@Getter
public class MenuReqDto {
    private String menuCategory;

    @NotEmpty
    private String name;

    private String description;

    @NotNull
    private BigDecimal price;
    private BigDecimal cost;
    private MultipartFile image;
    private Boolean isActive;

    private MenuCategory menuCategoryEntity;
}
