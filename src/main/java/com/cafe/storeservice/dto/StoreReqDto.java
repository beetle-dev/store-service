package com.cafe.storeservice.dto;


import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreReqDto {

    private String name;
    private String address;
    private String phone;
    private LocalTime openTime;
    private LocalTime closeTime;
}
