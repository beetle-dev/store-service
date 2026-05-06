package com.cafe.storeservice.dto.store;

import com.cafe.storeservice.domain.store.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreResDto {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private LocalTime openTime;
    private LocalTime closeTime;
    private boolean active;

    public static StoreResDto from(Store store) {
        return StoreResDto.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .phone(store.getPhone())
                .email(store.getEmail())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .active(store.isActive())
                .build();
    }
}
