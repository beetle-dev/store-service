package com.cafe.storeservice.dto.menu;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Setter
@Getter
public class MenuCreateReqDto {
    @NotNull
    private Long menuCategoryId;

    @NotEmpty
    private String name;

    @NotNull
    private BigDecimal price;

    private String description;
    private BigDecimal cost;
    private MultipartFile image;
}
