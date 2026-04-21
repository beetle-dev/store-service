package com.cafe.storeservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemReqDto {
    private Long menuId;
    private Integer quantity;
}
