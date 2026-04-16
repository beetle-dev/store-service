package com.cafe.storeservice.dto;

import com.cafe.storeservice.domain.Ingredient;
import com.cafe.storeservice.domain.StoreInventory;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
public class StoreInventoryResDto {
    private Long id;
    private Ingredient ingredient;
    private BigDecimal currentStock;
    private BigDecimal minStock;
    private LocalDateTime updatedAt;
    private Boolean isLow;

    public static StoreInventoryResDto from (StoreInventory storeInventory) {
        return StoreInventoryResDto.builder()
                .id(storeInventory.getId())
                .ingredient(storeInventory.getIngredient())
                .currentStock(storeInventory.getCurrentStock())
                .minStock(storeInventory.getMinStock())
                .updatedAt(storeInventory.getUpdatedAt())
                .isLow(currentStock.compareTo(minStock) < 0)
                .build();
    }
}
