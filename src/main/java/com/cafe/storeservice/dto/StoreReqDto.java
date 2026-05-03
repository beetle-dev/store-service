package com.cafe.storeservice.dto;


import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreReqDto {

    private String name;
    private String address;

    @Pattern(regexp = "^[0-9\\-]{9,13}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String phone;

    private String email;

    private LocalTime openTime;
    private LocalTime closeTime;
}
