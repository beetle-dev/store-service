package com.cafe.storeservice.dto.store;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class ModifyStoreReqDto {

    private String name;
    private String address;

    @Pattern(regexp = "^[0-9\\-]{9,13}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String phone;

    @Email
    private String email;

    private LocalTime openTime;
    private LocalTime closeTime;
    private boolean active;
}
