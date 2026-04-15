package com.cafe.storeservice.dto;

import com.cafe.storeservice.domain.Store;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class StoreResDto {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private LocalTime openTime;
    private LocalTime closeTime;
    private boolean isActive;

    public static StoreResDto from(Store store) {
        return StoreResDto.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .phone(store.getPhone())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .isActive(store.isActive())
                .build();
    }
}
