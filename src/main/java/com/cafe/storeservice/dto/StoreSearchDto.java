package com.cafe.storeservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreSearchDto {
    private String name;
    private String address;
    private String phone;
    private Boolean isActive;

    @Min(0)
    private int page = 0;

    @Max(100)
    private int size = 20;

    private String sort = "createdAt";

    @Pattern(regexp = "^(ASC|DESC)$", message = "direction은 ASC 또는 DESC만 허용됩니다.")
    private String direction = "DESC";
}
