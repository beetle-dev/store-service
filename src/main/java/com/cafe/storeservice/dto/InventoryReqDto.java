package com.cafe.storeservice.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class InventoryReqDto {
    private Long ingredientId;
    private BigDecimal quantity;
}
