package com.cafe.storeservice.dto.inventory;

import com.cafe.storeservice.domain.inventory.ChangeType;
import com.cafe.storeservice.domain.inventory.InventoryLog;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class InventoryLogResDto {
    private Long id;
    private Long ingredientId;
    private String ingredientName;
    private ChangeType changeType;
    private BigDecimal quantity;
    private BigDecimal stockAfter;
    private String note;
    private String performedBy;
    private LocalDateTime createdAt;

    public static InventoryLogResDto from(InventoryLog inventoryLog) {
        return InventoryLogResDto.builder()
                .id(inventoryLog.getId())
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
