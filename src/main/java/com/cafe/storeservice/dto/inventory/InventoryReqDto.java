package com.cafe.storeservice.dto.inventory;

import com.cafe.storeservice.domain.ChangeType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class InventoryReqDto {

    @NotNull
    private Long ingredientId;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private ChangeType changeType;

    private String note;
}
