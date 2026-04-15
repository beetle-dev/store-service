package com.cafe.storeservice.dto;

import com.cafe.storeservice.domain.ChangeType;
import com.cafe.storeservice.domain.Ingredient;
import com.cafe.storeservice.domain.InventoryLog;
import com.cafe.storeservice.domain.Store;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public class InventoryLogResDto {
    private Long id;
    private Store store;
    private Ingredient ingredient;
    private ChangeType changeType;
    private BigDecimal quantity;
    private BigDecimal stockAfter;
    private String note;
    private Long performedBy;
    private LocalDateTime createdAt;

    public static InventoryLogResDto from(InventoryLog inventoryLog) {
        return InventoryLogResDto.builder()
                .id(inventoryLog.getId())
                .store(inventoryLog.getStore())
                .ingredient(inventoryLog.getIngredient())
                .changeType(inventoryLog.getChangeType())
                .quantity(inventoryLog.getQuantity())
                .stockAfter(inventoryLog.getStockAfter())
                .note(inventoryLog.getNote())
                .performedBy(inventoryLog.getPerformedBy())
                .createdAt(inventoryLog.getCreatedAt())
                .build();
    }
}
