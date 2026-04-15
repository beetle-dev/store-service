package com.cafe.storeservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreSearchDto extends SearchDto {
    private String name;
    private String address;
    private String phone;
    private Boolean isActive;
}
