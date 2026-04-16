package com.cafe.storeservice.dto;

import com.cafe.storeservice.domain.ChangeType;
import com.cafe.storeservice.domain.Ingredient;
import com.cafe.storeservice.domain.InventoryLog;
import com.cafe.storeservice.domain.Store;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class InventoryLogResDto {
    private Long id;
    private Long storeId;
    private Long ingredientId;
    private String ingredientName;
    private ChangeType changeType;
    private BigDecimal quantity;
    private BigDecimal stockAfter;
    private String note;
    private Long performedBy;
    private LocalDateTime createdAt;

    public static InventoryLogResDto from(InventoryLog inventoryLog) {
        return InventoryLogResDto.builder()
                .id(inventoryLog.getId())
                .storeId(inventoryLog.getStore().getId())
                .ingredientId(inventoryLog.getIngredient().getId())
                .ingredientName(inventoryLog.getIngredient().getName())
                .changeType(inventoryLog.getChangeType())
                .quantity(inventoryLog.getQuantity())
                .stockAfter(inventoryLog.getStockAfter())
                .note(inventoryLog.getNote())
                .performedBy(inventoryLog.getPerformedBy())
                .createdAt(inventoryLog.getCreatedAt())
                .build();
    }
}
