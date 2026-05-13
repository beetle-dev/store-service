package com.cafe.storeservice.dto.menu;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Setter
@Getter
public class MenuModifyReqDto {
    private Long menuCategoryId;
    private String name;
    private BigDecimal price;
    private String description;
    private BigDecimal cost;
    private MultipartFile image;
    private Boolean isActive;
}
