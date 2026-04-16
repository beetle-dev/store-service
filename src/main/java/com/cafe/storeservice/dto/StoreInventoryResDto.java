package com.cafe.storeservice.dto;

import com.cafe.storeservice.domain.StoreInventory;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
public class StoreInventoryResDto {
    private Long id;
    private Long ingredientId;
    private String ingredientName;
    private String ingredientUnit;
    private BigDecimal currentStock;
    private BigDecimal minStock;
    private LocalDateTime updatedAt;
    private Boolean isLow;

    public static StoreInventoryResDto from(StoreInventory storeInventory) {
        return StoreInventoryResDto.builder()
                .id(storeInventory.getId())
                .ingredientId(storeInventory.getIngredient().getId())
                .ingredientName(storeInventory.getIngredient().getName())
                .ingredientUnit(storeInventory.getIngredient().getUnit())
                .currentStock(storeInventory.getCurrentStock())
                .minStock(storeInventory.getMinStock())
                .updatedAt(storeInventory.getUpdatedAt())
                .isLow(storeInventory.getCurrentStock()
                        .compareTo(storeInventory.getMinStock()) < 0)
                .build();
    }
}
