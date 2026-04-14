package com.cafe.storeservice.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreReqDto {

    @NotEmpty
    private String name;

    private String address;
    private String phone;
    private LocalTime openTime;
    private LocalTime closeTime;
}
